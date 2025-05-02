package org.ekgns33.commerce.product.api.dto;

public enum ProductStatus {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE"),
  DELETED("DELETED");

  private final String status;

  ProductStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
