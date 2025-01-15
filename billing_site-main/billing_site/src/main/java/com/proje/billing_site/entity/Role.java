package com.proje.billing_site.entity;

public enum Role {
    USER("Normal kullanıcı"), // Kullanıcı rolü
    ADMIN("Yönetici kullanıcı"); // Yönetici rolü

    private final String description; // Rolün açıklaması

    // Constructor
    Role(String description) {
        this.description = description;
    }

    // Açıklamayı döndüren getter
    public String getDescription() {
        return description;
    }

    public static Role fromDescription(String description) {
        for (Role role : Role.values()) {
            if (role.getDescription().equalsIgnoreCase(description)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Geçersiz rol: " + description);
    }



}
