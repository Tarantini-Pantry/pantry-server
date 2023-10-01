terraform {
   required_providers {
      aws = {
         source  = "hashicorp/aws"
         version = "~> 4.16"
      }
   }

   required_version = ">= 1.2.0"
}

provider "aws" {
   region = var.aws_region
}

provider "aws" {
   alias = "acm"

   region = var.acm_region
}

data "aws_availability_zones" "available" {
   state = "available"
}

// VPC Creation
resource "aws_vpc" "pantry_vpc" {
   cidr_block           = var.vpc_cidr_block
   enable_dns_hostnames = true
   enable_dns_support   = true

   tags = {
      Name = "pantry_vpc"
   }
}

// Internet Gateway Creation
resource "aws_internet_gateway" "pantry_igw" {
   vpc_id = aws_vpc.pantry_vpc.id

   tags = {
      Name = "pantry_igw"
   }
}

// Public/Private Subnet Creation
resource "aws_subnet" "pantry_public_subnet" {
   count = var.subnet_configuration.public.count

   vpc_id = aws_vpc.pantry_vpc.id

   cidr_block        = var.subnet_configuration.public.cidr_blocks[count.index]
   availability_zone = data.aws_availability_zones.available.names[count.index]

   tags = {
      Name = "pantry_public_subnet_${count.index}"
   }
}

resource "aws_subnet" "pantry_private_subnet" {
   count = var.subnet_configuration.private.count

   vpc_id = aws_vpc.pantry_vpc.id

   cidr_block        = var.subnet_configuration.private.cidr_blocks[count.index]
   availability_zone = data.aws_availability_zones.available.names[count.index]

   tags = {
      Name = "pantry_private_subnet_${count.index}"
   }
}

// Route Table + Routes Creation
resource "aws_route_table" "pantry_public_rt" {
   vpc_id = aws_vpc.pantry_vpc.id

   route {
      cidr_block = "0.0.0.0/0"
      gateway_id = aws_internet_gateway.pantry_igw.id
   }
}

resource "aws_route_table_association" "pantry_public_rta" {
   count = var.subnet_configuration.public.count

   route_table_id = aws_route_table.pantry_public_rt.id
   subnet_id      = aws_subnet.pantry_public_subnet[count.index].id
}

resource "aws_route_table_association" "pantry_private_rta" {
   count = var.subnet_configuration.private.count

   route_table_id = aws_route_table.pantry_public_rt.id
   subnet_id      = aws_subnet.pantry_private_subnet[count.index].id
}

// Security Group Creation
resource "aws_security_group" "pantry_web_sg" {
   vpc_id = aws_vpc.pantry_vpc.id

   name        = "pantry_web_sg"
   description = "Security group for pantry web servers"

   ingress {
      description = "Allow all traffic through HTTP"
      from_port   = "8080"
      to_port     = "8080"
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
   }

   ingress {
      description = "Allow SSH from my computer"
      from_port   = "22"
      to_port     = "22"
      protocol    = "tcp"
      cidr_blocks = ["0.0.0.0/0"]
   }

   egress {
      description = "Allow all outbound traffic"
      from_port   = 0
      to_port     = 0
      protocol    = "-1"
      cidr_blocks = ["0.0.0.0/0"]
   }

   tags = {
      Name = "pantry_web_sg"
   }
}

resource "aws_security_group" "pantry_db_sg" {
   vpc_id = aws_vpc.pantry_vpc.id

   name        = "pantry_db_sg"
   description = "Security group for pantry database servers"

   ingress {
      description     = "Allow Postgres traffic from only the web sg"
      from_port       = "5432"
      to_port         = "5432"
      protocol        = "tcp"
      security_groups = [aws_security_group.pantry_web_sg.id]
   }

   tags = {
      Name = "pantry_db_sg"
   }
}

// DB Subnet Creation
resource "aws_db_subnet_group" "pantry_db_subnet_group" {
   name        = "pantry_db_subnet_group"
   description = "DB subnet group for Pantry"

   subnet_ids = [for subnet in aws_subnet.pantry_private_subnet : subnet.id]
}
