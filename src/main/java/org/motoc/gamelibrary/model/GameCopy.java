package org.motoc.gamelibrary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.motoc.gamelibrary.model.enumeration.GeneralStateEnum;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A game, it describe a copy
 *
 * @author RouzicJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "objectCode"))
public class GameCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Pattern(regexp = "^[1-9]{1,5}$")
    @Column(nullable = false)
    private long objectCode;

    @DecimalMin(value = "0.0", inclusive = true, message = "Price value cannot be below 0.0")
    @Digits(integer = 10, fraction = 2, message = "Price maximum integer part is 10, maximum fractional part is 2")
    private BigDecimal price;

    /**
     * The location of the game in premises
     */
    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @PastOrPresent(message = "Date of purchase must be in the past or present")
    private LocalDate dateOfPurchase;

    @PastOrPresent(message = "Date of purchase must be in the past or present")
    @NotNull(message = "Date of purchase cannot be null")
    @Column(nullable = false)
    private LocalDate registerDate;

    @NotBlank(message = "Wear condition cannot be null or blank")
    @Column(nullable = false)
    private String wearCondition;

    @NotNull(message = "General State cannot be null")
    @Column(nullable = false)
    private GeneralStateEnum generalState;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_game")
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_seller")
    private Seller seller;

    @OneToMany(mappedBy = "gameCopy")
    private Set<Loan> loans = new HashSet<>();

    // Helper methods

    public void addLoan(Loan loan) {
        this.loans.add(loan);
        loan.setGameCopy(this);
    }

    public void removeLoan(Loan loan) {
        this.loans.remove(loan);
        loan.setGameCopy(null);
    }

    public void addSeller(Seller seller) {
        this.setSeller(seller);
        seller.getGameCopies().add(this);
    }

    public void removeSeller(Seller seller) {
        this.setSeller(null);
        seller.getGameCopies().remove(this);
    }

    // addGame/removeGame methods are not needed because adding game is mandatory at the creation of this object
}