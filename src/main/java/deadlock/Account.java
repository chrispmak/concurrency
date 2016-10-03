package deadlock;

/**
 * Created by ponyboy on 10/3/2016.
 */
public class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acct1, Account acc2, int amount) {
        acct1.withdraw(amount);
        acc2.deposit(amount);
    }
}
