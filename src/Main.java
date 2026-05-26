import ui.AdminUI;
import ui.ConsumerUI;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AdminUI aui = new AdminUI(scanner);
        aui.show();

        ConsumerUI cui = new ConsumerUI(scanner);
        cui.show();
    }
}