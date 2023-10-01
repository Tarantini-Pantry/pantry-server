resource "aws_route53_zone" "kitchen_pantry_zone" {
   name = "kitchen-pantry.com"
}

resource "aws_acm_certificate" "pantry_certificate" {
   domain_name       = "api.kitchen-pantry.com"
   validation_method = "DNS"
}

resource "aws_route53_record" "pantry_cert_dns" {
   allow_overwrite = true
   name            = tolist(aws_acm_certificate.pantry_certificate.domain_validation_options)[0].resource_record_name
   records         = [tolist(aws_acm_certificate.pantry_certificate.domain_validation_options)[0].resource_record_value]
   type            = tolist(aws_acm_certificate.pantry_certificate.domain_validation_options)[0].resource_record_type
   zone_id         = aws_route53_zone.kitchen_pantry_zone.zone_id
   ttl             = 60
}

resource "aws_acm_certificate_validation" "pantry_cert_validate" {
   certificate_arn         = aws_acm_certificate.pantry_certificate.arn
   validation_record_fqdns = [aws_route53_record.pantry_cert_dns.fqdn]
}

resource "aws_route53_record" "kitchen_pantry_NS" {
   zone_id = aws_route53_zone.kitchen_pantry_zone.zone_id
   name    = "kitchen-pantry.com."
   type    = "NS"
   ttl     = "172800"
   records = ["ns-1776.awsdns-30.co.uk.", "ns-875.awsdns-45.net.", "ns-21.awsdns-02.com.", "ns-1435.awsdns-51.org."]
}

resource "aws_route53_record" "kitchen_pantry_SOA" {
   zone_id = aws_route53_zone.kitchen_pantry_zone.zone_id
   name    = "kitchen-pantry.com."
   type    = "SOA"
   ttl     = "900"
   records = ["ns-1776.awsdns-30.co.uk. awsdns-hostmaster.amazon.com. 1 7200 900 1209600 86400"]
}

resource "aws_route53_record" "kitchen_pantry_ec2" {
   zone_id = aws_route53_zone.kitchen_pantry_zone.zone_id
   name    = "api.kitchen-pantry.com."
   type    = "A"

   alias {
      name                   = aws_elb.pantry_elb.dns_name
      zone_id                = aws_elb.pantry_elb.zone_id
      evaluate_target_health = true
   }
}
