output "web_public_ip" {
   description = "The public IP address of the web server"
   value       = aws_eip.pantry_web_eip[0].public_ip

   depends_on = [aws_eip.pantry_web_eip]
}

output "web_public_dns" {
   description = "The public DNS address of the web server"
   value       = aws_eip.pantry_web_eip[0].public_dns

   depends_on = [aws_eip.pantry_web_eip]
}

output "database_endpoint" {
   description = "The endpoint of the database"
   value       = aws_db_instance.pantry_database.address
}

output "database_port" {
   description = "The port of the database"
   value       = aws_db_instance.pantry_database.port
}

output "bucket_name" {
   description = "The bucket name of the website"
   value       = module.s3-static-website.website_bucket_id
}
