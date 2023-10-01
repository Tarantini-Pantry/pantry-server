resource "aws_secretsmanager_secret" "db_username_sm" {
   name = "database_username_secret"
}

resource "aws_secretsmanager_secret" "db_password_sm" {
   name = "database_password_secret"
}

resource "aws_secretsmanager_secret_version" "db_username_sv" {
   secret_id     = aws_secretsmanager_secret.db_username_sm.id
   secret_string = var.db_username
}

resource "aws_secretsmanager_secret_version" "db_password_sv" {
   secret_id     = aws_secretsmanager_secret.db_password_sm.id
   secret_string = var.db_password
}

resource "aws_db_instance" "pantry_database" {
   allocated_storage      = var.settings.database.allocated_storage
   max_allocated_storage  = var.settings.database.max_allocated_storage
   engine                 = var.settings.database.engine
   engine_version         = var.settings.database.engine_version
   instance_class         = var.settings.database.instance_class
   db_name                = var.settings.database.db_name
   username               = aws_secretsmanager_secret_version.db_username_sv.secret_string
   password               = aws_secretsmanager_secret_version.db_password_sv.secret_string
   db_subnet_group_name   = aws_db_subnet_group.pantry_db_subnet_group.id
   vpc_security_group_ids = [aws_security_group.pantry_db_sg.id]
   skip_final_snapshot    = var.settings.database.skip_final_snapshot
}
