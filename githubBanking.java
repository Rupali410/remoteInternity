
package bankingsystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;


public class githubBanking 
{
    
    static ArrayList<String> miniState = new ArrayList<>();
        static HashMap<Integer,Transaction> pass_book = new HashMap<>();
	static int bal = 0;
        static int serial_num =1;
        int c_num;
        String name;
	
	static Date d = new Date();
	static String date = new SimpleDateFormat("dd/MMM/yyyy").format(d);
	static String time = new SimpleDateFormat("HH:mm:ss").format(d);
        
        void credit(int credit_amt,File file_name) throws IOException
        {
                if(credit_amt>0)
                {
                    bal =bal+credit_amt;
                   
                    System.out.println("Amount Credited \n");
                    System.out.println("Now new amount " + bal);
                    System.out.println("\n");
                    A_T_M.pass_book.put(serial_num, new Transaction(c_num,name,0,credit_amt,bal));
				A_T_M.serial_num++;
                    A_T_M.miniState.add("Account is credited with Rs"+credit_amt+" on date "+date +"on time "+time);
                
                //file
                        FileWriter writer = new FileWriter(file_name);
			writer.write("Transaction Performed: Credit");
			writer.write("\r\n");
			writer.write("Amount Creditedt: "+credit_amt);
			writer.write("\r\n");
			writer.write("Balance: "+bal);
			writer.write("\r\n");
			writer.write("Date: "+date);
			writer.write("\r\n");
			writer.write("Time: "+time);
			writer.write("\r\n");
			writer.close();
                
                }
                
            }
           
        
        
        void debit(int debit_amt,File file_name) throws IOException
        {
                if(debit_amt>0)
                {
                    if(bal >=debit_amt)
                    {
                        bal =bal+debit_amt;
                   
                        System.out.println("Amount Credited \n");
                        System.out.println("Now new amount " + bal);
                        System.out.println("\n");
                        A_T_M.pass_book.put(serial_num, new Transaction(c_num,name,0,debit_amt,bal));
                        A_T_M.serial_num++;
                        A_T_M.miniState.add("Account is credited with Rs"+debit_amt+" on date "+date +"on time "+time);
                    
                        //file
                        FileWriter writer = new FileWriter(file_name);
			writer.write("Transaction Performed: Credit");
			writer.write("\r\n");
			writer.write("Amount Creditedt: "+debit_amt);
			writer.write("\r\n");
			writer.write("Balance: "+bal);
			writer.write("\r\n");
			writer.write("Date: "+date);
			writer.write("\r\n");
			writer.write("Time: "+time);
			writer.write("\r\n");
			writer.close();
                    
                    
                    }
                    else
                        System.out.println("No Balance");
                }
                    else
                        System.out.println("No negative amount possible");
        }
        
            
        
        
        void passbook()
        {
            if(pass_book.isEmpty())
            {
                System.out.println("No Transactions");
            }
            else
            {
                System.out.println("***********************PASSBOOK*********************************");
		System.out.println("Serial_num   Cheque No.     Name      Debit amt     Credit amt      Bal        Date           time");
		for(HashMap.Entry<Integer,Transaction> m:pass_book.entrySet())
                {
                    Transaction tr = m.getValue();
					
                    System.out.print("   "+m.getKey()+"         "+tr.acc_num+"       ");
                    if(tr.acc_num==0)
                        System.out.print("     -"+"   ");
                    else
                        System.out.print("   "+tr.cus_name+"   ");

                    if(tr.debit_amt==0)
                        System.out.print("        -          ");
                    else
                        System.out.print("   "+tr.debit_amt+"           ");

                    if(tr.credit_amt==0)
                        System.out.println("-     "+tr.bal+"     "+tr.date+"     "+tr.time);
                    else
                        System.out.println(tr.credit_amt+"         "+tr.bal+"     "+tr.date+"     "+tr.time);


		}
            }
		 
        }
        
        void miniStatement()
        {
            for(int i=0;i<A_T_M.miniState.size();i++)
				System.out.println(A_T_M.miniState.get(i));
                    System.out.println("\n");
        }
    
    
    
    
    public static void main(String args[]) throws ClassNotFoundException, SQLException, IOException
    {
        githubBanking atm = new githubBanking();
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
                                    String uname = "hr";
                                    String pass = "hr";
                                    Class.forName("oracle.jdbc.driver.OracleDriver"); 
                                    Connection con = DriverManager.getConnection(url,uname,pass);
                                    System.out.println("Connection established");
                                    Statement query = con.createStatement();
                                    
                                    
        
        System.out.println("1.Already a user");
        System.out.println("2.creating an account");
        System.out.println("3.Exit");
        
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch(choice)
        {
            case 1 :    {
                
                            while(true)
                            {
                            System.out.println("Enter your Account id\n");
                            int card_num = sc.nextInt();
                            System.out.println("Enter your Account pin\n");
                            int pin = sc.nextInt();
                            
                            File file_name = new File(card_num+".txt"); 
                            
                            try{
                            String queryCheck = "SELECT * from Account where card_number ="+card_num+"";
                            ResultSet rs = query.executeQuery(queryCheck);
                            
                            if(rs.next())
                            {
                                System.out.println("You are Now loged In");
                                atm.account(card_num,file_name);
                            }
                            else
                            {
                                System.out.println("You are failed to login please enter the correct Details");
                                break;
                            }
                            }catch(Exception e)
                            {
                                e.printStackTrace();
                            }
        
                          }
                            
                           // break;
                        }
            case 2 :    {
                           
                            
                          
                            System.out.println("Enter your Card Type");
                            String acc_type1 = sc.nextLine();
                            System.out.println("\nEnter your Card Type");
                            String acc_type = sc.nextLine();
                            
                            System.out.println("\nEnter your card number");
                            int card_num = sc.nextInt();
                            System.out.println("\nEnter your Expiration Date");
                            System.out.println("\nenter month");
                            int month = sc.nextInt();
                            System.out.println("\nenter year");
                            int year = sc.nextInt();
                            String expiry_date = month +" / "+ year;
                            System.out.println("\nEnter CVV");
                            int cvv = sc.nextInt();
                            System.out.println("\nEnter your pin number");
                            int pin = sc.nextInt();
                             System.out.println("\nName ");
                            String name1 = sc.nextLine();
                            System.out.println("\nName ");
                            String name = sc.nextLine();
                            System.out.println("\nDOB ");
                            System.out.println("\nEnter date ");
                            int dated = sc.nextInt();
                            System.out.println("\nEnter month ");
                            int monthd = sc.nextInt();
                            System.out.println("\nEnter year ");
                            int yeard = sc.nextInt();
                            String dob = dated +" / "+ monthd +" / "+yeard ;
                            System.out.println("\nEnter the mobile number");
                            int mob = sc.nextInt();
                            
   
            
                                    System.out.println("\nyour account has been create");
                                 try
                                 {
                                    String sql = "insert into Account values ( ? , ? , ? , ? , ? , ? , ? , ? )";
        
                                    PreparedStatement st = con.prepareStatement(sql);
                                 
                                    st.setString(1, acc_type);
                                    st.setInt(2, card_num);
                                    st.setString(3, expiry_date);
                                    st.setInt(4, cvv);
                                    st.setInt(5, pin);
                                    st.setString(6, name);
                                    st.setString(7, dob);
                                    st.setInt(8, mob);
                                    System.out.println("gbg");
                                    
                                    
                                
 
                                    int a = st.executeUpdate();  
                                    if(a>0)
                                    {
                                        System.out.println("\nRow Updated");
                                    }
                                  
                                 } catch (SQLException se){
                                    se.printStackTrace();
                                    } finally {
                                    con.close();
                                    }
                                
                                File file_name = new File(card_num+".txt"); 
                            System.out.println(file_name);
                            if (file_name.createNewFile()) 
                            {  
                                System.out.println("\nNew File is created!");  
                            }
                            else
                            {  
                                System.out.println("\nFile already exists."); 
                               
                            }
                               account(card_num,file_name);
                                break;
                
                        }
            case 3 :    {
                            System.exit(0);
                            System.out.println("You are Exit now, Thanku!!!!");
                        }
                
            
        }
    }

    private static void account(int card_num , File file_name) throws ClassNotFoundException, SQLException, IOException
    {
        githubBanking atm = new githubBanking();
        Scanner sc = new Scanner(System.in);
         String url = "jdbc:oracle:thin:@localhost:1521:xe";
                                    String uname = "hr";
                                    String pass = "hr";
                                    Class.forName("oracle.jdbc.driver.OracleDriver"); 
                                    Connection con = DriverManager.getConnection(url,uname,pass);
                                    System.out.println("Connection established");
                                    Statement query = con.createStatement();
                             while(true)
                                         {
                 
                                            System.out.println("1. Credit");
                                            System.out.println("2. Debit");
                                            System.out.println("3. Passbook");
                                            System.out.println("4. Ministatements");
                                            System.out.println("5. Exit");
                                            System.out.println("Enter the number for Proceeding further");
             
                
                                            int credit_amt=0;
                                            int debit_amt=0;
                
             
                                            int input = sc.nextInt();
                                            switch(input)
                                            {
                                                case 1 :
                                                {
                                                       System.out.println("\nEnter the amount to credit");
                                                       credit_amt = sc.nextInt();
                                                       atm.credit(credit_amt,file_name);

                                                       break; 
                                                }
                                                case 2 :
                                                {

                                                       System.out.println("\nEnter the amount to debited");
                                                       debit_amt = sc.nextInt();
                                                       atm.debit(debit_amt,file_name);
                                                       break;
                                                }
                                                case 3 :
                                                {
                                                       atm.passbook();
                                                       break;
                                                }
                                                case 4 :
                                                {
                                                       atm.miniStatement();
                                                       break;
                                                }
                                                case 5 :
                                                {
                                                        System.exit(0);
                                                }
                                            }
                                            
                                            String sql = "CREATE TABLE t" +card_num +
                                                            //"(sno INTEGER not NULL, " +
                                                            //" particulars VARCHAR(255) not NULL, " + 
                                                            " (chequeNum INTEGER not NULL, " +
                                                            " credit INTEGER, " +
                                                            " debit INTEGER, " +
                                                            " balance INTEGER not NULL, " +
                                                            " d_ate VARCHAR(255) not NULL, " +
                                                            " t_ime VARCHAR(255) not NULL, " + 
                                                            " PRIMARY KEY ( chequeNum ))"; 

                                            query.executeUpdate(sql);
                                            System.out.println("Created table in given database...");


                                            String sql1 = "insert into t" + card_num + " values(  ? , ? , ? , ? , ? , ?)";
                                            PreparedStatement st = con.prepareStatement(sql1);



                                                                st.setInt(1, card_num);
                                                                st.setInt(2, debit_amt);
                                                                st.setInt(3, credit_amt);
                                                                st.setInt(4, bal);
                                                                st.setString(5, date);
                                                                st.setString(6, time); 

                                                                 int a = st.executeUpdate();
                                                             if(a>0)
                                                             {
                                                                System.out.println("ROw Upadated");
                                                             }
                                         }
        
      
    }
    
}
class Transaction extends githubBanking
{
    String cus_name;
	int acc_num;
	int debit_amt;
	int credit_amt;
        final int bal;
	
	Transaction(int acc_num, String cus_name, int debit_amt, int credit_amt,int bal)
	{
		this.cus_name = cus_name;
		this.credit_amt = credit_amt;
                this.debit_amt = debit_amt;
                this.bal = bal;
                this.acc_num = acc_num;
		
	}	
}
