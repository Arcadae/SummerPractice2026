import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.stream.Collectors;

public class BankAccount {

    //Owner name
    private String nameOfHolder;
    //Account balance
    private int accountBalance;
    //Registration date
    private LocalDateTime registrationDate;
    //Is account blocked
    private boolean blockingFlag;
    //Account number
    private String number;

    /*Default values initialization*/
    {
        accountBalance = 0;
        registrationDate = LocalDateTime.now();
        number = new Random()
            .ints(8, 0, 9)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining());
        blockingFlag = false;
    }

    /*End*/

    //Class constructor
    public BankAccount(String nameOfHolder) {
        this.nameOfHolder = nameOfHolder;
    }

    //Method for viewing data
    @Override
    public String toString() {
        //Formatting the opening date to string type
        String formattedRegistrationDate = registrationDate.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
        //Output account data
        return (
            "Account{" +
            "Holder's name: " +
            nameOfHolder +
            ", Account's balance = " +
            accountBalance +
            ", Registration date: " +
            formattedRegistrationDate +
            ", Is blocked: " +
            blockingFlag +
            ", Account's number: " +
            number +
            "}"
        );
    }

    //Method for replenishing the account balance
    public boolean deposit(int amount) {
        if (blockingFlag != true && amount > 0) {
            accountBalance += amount;
            System.out.println(
                String.format(
                    "%s! Your balance has been replenished by the amount of %d. Now your balance is up: %d",
                    nameOfHolder,
                    amount,
                    accountBalance
                )
            );
            return true;
        }
        System.out.println("The operation is impossible");
        return false;
    }

    //Method for withdrawing funds from the account
    public boolean withdraw(int amount) {
        if (blockingFlag != true && amount > 0 && amount <= accountBalance) {
            accountBalance -= amount;
            System.out.println(
                String.format(
                    "%s! Your balance has been debited by the amount of %d. Now your balance is up: %d",
                    nameOfHolder,
                    amount,
                    accountBalance
                )
            );
            return true;
        }
        System.out.println("The operation is impossible");
        return false;
    }

    //Method for transferring funds to another account
    public boolean transfer(BankAccount otherAccount, int amount) {
        if (
            blockingFlag != true &&
            otherAccount.isBlocked() != true &&
            amount > 0 &&
            amount <= accountBalance
        ) {
            accountBalance -= amount;
            otherAccount.accountBalance += amount;
            System.out.println(
                String.format(
                    "%s! From your balance to the user's balance %s the value was converted to an amount equal to %d. Now your balance is up: %d",
                    nameOfHolder,
                    otherAccount.nameOfHolder,
                    amount,
                    accountBalance
                )
            );
            return true;
        }
        System.out.println("The operation is impossible");
        return false;
    }

    private boolean isBlocked() {
        return blockingFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount account = (BankAccount) o;

        if (!nameOfHolder.equals(account.nameOfHolder)) return false;
        if (accountBalance != account.accountBalance) return false;
        if (!registrationDate.equals(account.registrationDate)) return false;
        if (blockingFlag != account.blockingFlag) return false;
        return number.equals(account.number);
    }

    @Override
    public int hashCode() {
        int result = accountBalance;
        result = 31 * result + nameOfHolder.hashCode();
        result = 31 * result + registrationDate.hashCode();
        result = 31 * result + Boolean.hashCode(blockingFlag);
        result = 31 * result + number.hashCode();
        return result;
    }
}
