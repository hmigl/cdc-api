package com.hmigl.cdc.payment_flow;

import com.hmigl.cdc.country_state.Country;
import com.hmigl.cdc.country_state.State;
import com.hmigl.cdc.coupon.AppliedCoupon;
import com.hmigl.cdc.coupon.Coupon;
import com.hmigl.cdc.shared.CpfOrCnpj;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.util.Assert;

import java.util.function.Function;

// 5
@Entity
public class Purchase {
    private @Id @GeneratedValue Long id;

    private @NotBlank String name;
    private @NotBlank String lastName;
    private @NotBlank @Email String email;
    private @NotBlank String cellphone;
    private @NotBlank @CpfOrCnpj String document;
    private @NotBlank String address;
    private @NotBlank String complement;
    private @NotBlank String city;
    private @NotBlank String cep;

    @ManyToOne
    @JoinColumn(name = "country_id_fk")
    private @NotNull Country country;

    @ManyToOne
    @JoinColumn(name = "state_id_fk")
    private @NotNull State state;

    @OneToOne(
            mappedBy = "purchase",
            cascade = {CascadeType.PERSIST})
    private @NotNull Order order;

    @Embedded private AppliedCoupon appliedCoupon;

    @Deprecated
    protected Purchase() {}

    private Purchase(Builder builder) {
        this.name = builder.name;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.cellphone = builder.cellphone;
        this.document = builder.document;
        this.address = builder.address;
        this.complement = builder.complement;
        this.city = builder.city;
        this.cep = builder.cep;
        this.country = builder.country;
        this.state = builder.state;
        this.order = builder.createOrderFunction.apply(this);
    }

    public void applyCoupon(@NotNull Coupon coupon) {
        Assert.notNull(coupon, "a coupon must not be null");
        Assert.isTrue(coupon.isValid(), "only a valid coupon can be associated with a purchase");
        Assert.state(
                this.appliedCoupon == null,
                "once a purchase is associated with a coupon, it can never change");
        /*
         * Why not 'this.coupon = coupon'?
         * Well, what happens with the purchases associated with this coupon if its discount percentage changes?
         * Will discounts of purchases made in different moments with the same coupon be different? How about the expiration date?
         */
        this.appliedCoupon = new AppliedCoupon(coupon);
    }

    public Long getId() {
        return id;
    }

    public static final class Builder {
        private @NotBlank String name;
        private @NotBlank String lastName;
        private @NotBlank @Email String email;
        private @NotBlank String cellphone;
        private @NotBlank @CpfOrCnpj String document;
        private @NotBlank String address;
        private @NotBlank String complement;
        private @NotBlank String city;
        private @NotBlank String cep;
        private @NotNull Country country;
        private State state;
        private Function<Purchase, Order> createOrderFunction;

        public Builder name(@NotBlank String name) {
            Assert.hasLength(name, "name must not be blank");
            this.name = name;
            return this;
        }

        public Builder lastName(@NotBlank String lastName) {
            Assert.hasLength(lastName, "lastName must not be blank");
            this.lastName = lastName;
            return this;
        }

        public Builder email(@NotBlank String email) {
            Assert.hasLength(email, "email must not be blank");

            EmailValidator emailValidator = new EmailValidator();
            emailValidator.initialize(null);
            Assert.isTrue(emailValidator.isValid(email, null), "");

            this.email = email;
            return this;
        }

        public Builder cellphone(@NotBlank String cellphone) {
            Assert.hasLength(cellphone, "cellphone must not be blank");
            this.cellphone = cellphone;
            return this;
        }

        public Builder document(@NotBlank String document) {
            Assert.hasLength(document, "document must not be blank");

            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.initialize(null);
            CNPJValidator cnpjValidator = new CNPJValidator();
            cnpjValidator.initialize(null);
            Assert.isTrue(
                    cpfValidator.isValid(document, null) || cnpjValidator.isValid(document, null),
                    "document must be either CPF or CNPJ");

            this.document = document;
            return this;
        }

        public Builder address(@NotBlank String address) {
            Assert.hasLength(address, "address must not be blank");
            this.address = address;
            return this;
        }

        public Builder complement(@NotBlank String complement) {
            Assert.hasLength(complement, "complement must not be blank");
            this.complement = complement;
            return this;
        }

        public Builder city(@NotBlank String city) {
            Assert.hasLength(city, "city must not be blank");
            this.city = city;
            return this;
        }

        public Builder cep(@NotBlank String cep) {
            Assert.hasLength(cep, "cep must not be blank");
            this.cep = cep;
            return this;
        }

        public Builder country(@NotNull Country country) {
            Assert.notNull(country, "country must not be null");
            this.country = country;
            return this;
        }

        public Builder state(@NotNull State state) {
            Assert.notNull(country, "country must not be null");
            Assert.notNull(state, "state must not be null");
            Assert.isTrue(
                    state.belongsTo(this.country), "state is not associated with this country");
            this.state = state;
            return this;
        }

        public Builder order(Function<Purchase, Order> createOrderFunction) {
            this.createOrderFunction = createOrderFunction;
            return this;
        }

        public Purchase build() {
            return new Purchase(this);
        }
    }
}
