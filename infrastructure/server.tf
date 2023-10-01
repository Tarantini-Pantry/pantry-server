resource "aws_instance" "pantry_server" {
   count = var.settings.web-app.count

   ami                         = var.settings.web-app.ami
   instance_type               = var.settings.web-app.instance_type
   subnet_id                   = aws_subnet.pantry_public_subnet[count.index].id
   key_name                    = var.settings.web-app.key_pair
   vpc_security_group_ids      = [aws_security_group.pantry_web_sg.id]
   user_data_replace_on_change = true

   tags = {
      Name = "pantry_server_${count.index}"
   }
}

resource "aws_eip" "pantry_web_eip" {
   count = var.settings.web-app.count

   instance = aws_instance.pantry_server[count.index].id

   tags = {
      Name = "pantry_web_eip"
   }
}

resource "aws_elb" "pantry_elb" {
   name            = "pantry-elb"
   subnets         = [aws_subnet.pantry_public_subnet[0].id]
   security_groups = [aws_security_group.pantry_lb_sg.id]

   listener {
      instance_port     = 8080
      instance_protocol = "http"
      lb_port           = 80
      lb_protocol       = "http"
   }

   listener {
      instance_port      = 8080
      instance_protocol  = "http"
      lb_port            = 443
      lb_protocol        = "https"
      ssl_certificate_id = aws_acm_certificate.pantry_certificate.id
   }

   health_check {
      healthy_threshold   = 2
      unhealthy_threshold = 2
      timeout             = 3
      target              = "HTTP:8080/startup"
      interval            = 30
   }

   instances                   = [aws_instance.pantry_server[0].id]
   cross_zone_load_balancing   = true
   idle_timeout                = 400
   connection_draining         = true
   connection_draining_timeout = 400

   tags = {
      Name = "pantry_elb"
   }
}
