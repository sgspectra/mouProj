import javax.mail.MessagingException;
import java.util.Scanner;

class testEmail{
    public static void main(String[] args) throws MessagingException {
        System.out.println("Please enter username:");
        Scanner in = new Scanner(System.in);
        String usr = in.nextLine();
        System.out.println("Please enter password:");
        String pwd = in.nextLine();
        System.out.println("Please enter recipient");
        String rcp = in.nextLine();
        System.out.println("Please enter your Subject:");
        String sub = in.nextLine();
        System.out.println("please enter message:");
        String msg = in.nextLine();
        GoogleMail.Send(usr,pwd,rcp,sub,msg);
    }
}