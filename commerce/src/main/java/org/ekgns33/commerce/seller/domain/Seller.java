package org.ekgns33.commerce.seller.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.ekgns33.commerce.common.audit.CreateAudit;

@Table(name = "sellers")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seller extends CreateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "logo_url")
  private String logoUrl;

  @Column(name = "rating")
  private BigDecimal rating;

  @Column(name = "contact_email")
  private String contactEmail;

  @Column(name = "contact_phone")
  private String contactPhone;

  private Seller(
      Long id,
      String name,
      String description,
      String logoUrl,
      BigDecimal rating,
      String contactEmail,
      String contactPhone) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.logoUrl = logoUrl;
    this.rating = rating;
    this.contactEmail = contactEmail;
    this.contactPhone = contactPhone;
  }
}
