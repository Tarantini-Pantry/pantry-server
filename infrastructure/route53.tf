resource "aws_route53_zone" "kitchen_pantry_zone" {
   name = "kitchen-pantry.com"
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
