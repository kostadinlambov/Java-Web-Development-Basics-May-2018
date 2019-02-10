package main.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    private String id;

    private String name;

    private Double price;

    private String description;

    private ProductType type;

    public Product() {
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(nullable = false, columnDefinition = "VARCHAR(30)")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    /*
    * <a href="/bullshit" class="col-md-2">
                    <div class="product p-1 chushka-bg-color rounded-top rounded-bottom">
                        <h5 class="text-center mt-3">Chushkopek</h5>
                        <hr class="hr-1 bg-white"/>
                        <p class="text-white text-center">
                            A universal tool for peking chushkas.
                        </p>
                        <hr class="hr-1 bg-white"/>
                        <h6 class="text-center text-white mb-3">$500</h6>
                    </div>
                </a>
                */

    public String extractHomeView() {
        return (new StringBuilder()
            .append("<a href=\"/products/details/" + this.getId() + "\" class=\"col-md-2\">")
                .append("<div class=\"product p-1 chushka-bg-color rounded-top rounded-bottom\">")
                .append("<h5 class=\"text-center mt-3\">" + this.getName() + "</h5>")
                .append("<hr class=\"hr-1 bg-white\"/>")
                .append("<p class=\"text-white text-center\">")
                .append(this.getDescription())
                .append("</p>")
                .append("<hr class=\"hr-1 bg-white\"/>")
                .append("<h6 class=\"text-center text-white mb-3\">$" + this.getPrice() + "</h6>")
                .append("</div>")
                .append("</a>")
        ).toString();
    }
}
