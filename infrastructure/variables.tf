variable "aws_region" {
   default = "us-east-2"
}

variable "vpc_cidr_block" {
   default = "10.0.0.0/16"
}

variable "subnet_configuration" {
   default = {
      public = {
         count       = 1,
         cidr_blocks = [
            "10.0.1.0/24",
            "10.0.2.0/24",
            "10.0.3.0/24",
            "10.0.4.0/24"
         ]
      },
      private = {
         count       = 2,
         cidr_blocks = [
            "10.0.101.0/24",
            "10.0.102.0/24",
            "10.0.103.0/24",
            "10.0.104.0/24"
         ]
      }
   }
}

variable "db_username" {
   type      = string
   sensitive = true
}

variable "db_password" {
   type      = string
   sensitive = true
}

variable "settings" {
   default = {
      database = {
         allocated_storage     = 10,
         max_allocated_storage = 20,
         engine                = "postgres",
         engine_version        = "12.16",
         instance_class        = "db.t2.micro",
         db_name               = "pantry_database",
         skip_final_snapshot   = true
      },
      "web-app" = {
         count         = 1,
         instance_type = "t2.micro"
         ami           = "ami-0dabbb52b12fcfca9"
         key_pair      = "pantry_keypair"
      }
   }
}
