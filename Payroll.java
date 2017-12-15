import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.sql.*;
import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

class PayrollLogin extends JFrame implements ActionListener
{
JLabel lblUid,lblPas;
JComboBox cmbUid;
JPasswordField  txtPas;
JButton btnOk,btnCan;
PayrollLogin()
{
super("Welcome to EPS");
setLayout(null);
setBounds(500,300,300,225);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
setResizable(false);
add(cmbUid=new JComboBox());
add(txtPas=new JPasswordField(""));
add(btnOk=new JButton("Log In"));
add(btnCan=new JButton("Cancel"));
add(lblUid=new JLabel("User Id"));
add(lblPas=new JLabel("Password"));

btnOk.addActionListener(this);
btnCan.addActionListener(this);


lblUid.setBounds(10,50,100,35);  cmbUid.setBounds(110,60,170,25);
lblPas.setBounds(10,90,100,35); txtPas.setBounds(110,100,170,25);

btnOk.setBounds(110,150,80,25);
btnCan.setBounds(200,150,80,25);
cmbUid.addItem("Administrator");
}
public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==btnOk)
{
if(txtPas.getText().equals("kspl"))
{
new PayrollMDI();
this.dispose();
}
else
{JOptionPane.showMessageDialog(this, "Password Mismatched."  ,"Error", JOptionPane.ERROR_MESSAGE); 
txtPas.requestFocus();
}
}
else if(ae.getSource()==btnCan)
{
System.exit(0);
}
}
public static void main(String kork[])
{
new PayrollLogin();
}

}
class DeviceUpdates extends JFrame implements ActionListener
{
JButton imp,ext;
JLabel dt;
DeviceUpdates()
{
super("EPS - Device Updates");
setLayout(null);
setBounds(300,300,300,200);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
setResizable(false);
add(imp=new JButton("Import"));
add(ext=new JButton("Exit"));
add(dt=new JLabel(""));
imp.setBounds(100,100,100,25);
ext.setBounds(100,130,100,25);
dt.setBounds(80,50,200,25);
imp.addActionListener(this);
ext.addActionListener(this);
dt.setText("Updates Till =  " +  new SimpleDateFormat("dd/MM/yyyy").format(new Date())); 
}
public void actionPerformed(ActionEvent ae)
{
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection con = DriverManager.getConnection("jdbc:odbc:prs");
Statement stmt = con.createStatement();
ResultSet rs;
PreparedStatement pmt=null;
if(ae.getSource()==ext)
{
this.dispose();
}
else if(ae.getSource()==imp)
{
int c,c9=0;
String r,en="",dat="";
FileReader fr = new FileReader("AGL_001.txt");
BufferedReader br =new BufferedReader(fr);
br.readLine();
while((r = br.readLine())!=null)
{
en="";dat="";
c9=0;
for(int i=0;i<r.length();i++)
{
if((int)r.charAt(i)==9)
{
c9++;
if(c9==2)
{
for(int j=i+1;(int)r.charAt(j)!=9;j++)
en+=r.charAt(j);
}
else if(c9==8)
{
for(int j=i+1,cj=0;cj<10;cj++,j++)
dat+=r.charAt(j);
}
}
}
pmt = con.prepareStatement("select * from attendance where emp_id=? and dt=?");
pmt.setInt(1,Integer.parseInt(en));
pmt.setString(2,dat);
rs = pmt.executeQuery();
if(!rs.next())
{
pmt=con.prepareStatement("insert into attendance (emp_id,dt) values(?,?)");
pmt.setInt(1,new Integer(en));
pmt.setString(2,dat);
pmt.executeUpdate();
}
//System.out.println(en+" "+dat);
}
br.close();
fr.close();
JOptionPane.showMessageDialog(this, "Updated successfully..."  ,"Message", JOptionPane.PLAIN_MESSAGE); 
}
}
catch(Exception e)
{
System.out.println(e.toString());
}
}
public static void main(String kspl[])
{
new DeviceUpdates();
}
}

class PayrollMDI extends JFrame implements ActionListener
{
JMenuBar mb;
JMenu ops;
JMenuItem newEmp,empRep,devUps,chgPas,abt,logOut,exit;
JLabel lblTtl,lblSubt,s1,s2,s3;
Font f,f2;
PayrollMDI()
{
super("EPS - Home Form");
setLayout(null);
setBounds(1,1,1024,768);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
setVisible(true);
setResizable(true);
setJMenuBar(mb=new JMenuBar());
mb.add(ops=new JMenu("Options"));
ops.add(newEmp=new JMenuItem("New Employee"));
ops.add(empRep=new JMenuItem("Employee Report"));
ops.add(devUps=new JMenuItem("Device Updates"));
ops.add(chgPas=new JMenuItem("Change Password"));
ops.add(abt=new JMenuItem("About..."));
ops.add(logOut=new JMenuItem("Log Out"));
ops.add(exit=new JMenuItem("Exit"));
newEmp.addActionListener(this);
empRep.addActionListener(this);
devUps.addActionListener(this);
chgPas.addActionListener(this);
abt.addActionListener(this);
logOut.addActionListener(this);
exit.addActionListener(this);
add(lblTtl=new JLabel("EMPLOYEE PAYROLL SYSTEM"));
add(lblSubt=new JLabel("SUBMITTED BY"));
add(s1=new JLabel("Mr. KALBANDI S.G."));
add(s2=new JLabel("Mr. PATEL M.I."));
add(s3=new JLabel("Mr. PATIL D.S."));
lblTtl.setFont(new Font("Arial Black",Font.BOLD,25));
lblSubt.setFont(new Font("Arial",Font.BOLD,20));
s1.setFont(new Font("Arial",Font.BOLD,15));
s2.setFont(new Font("Arial",Font.BOLD,15));
s3.setFont(new Font("Arial",Font.BOLD,15));
lblTtl.setBounds(10,10,550,50);
lblSubt.setBounds(10,70,550,30);
s1.setBounds(10,120,550,20);
s2.setBounds(10,150,550,20);
s3.setBounds(10,180,550,20);
}
public void actionPerformed(ActionEvent ae)
{
if(ae.getSource()==newEmp)
new EmpEntry();
else if(ae.getSource()==empRep)
new EmpRep();
else if(ae.getSource()==devUps)
new DeviceUpdates();
else if(ae.getSource()==exit)
System.exit(0);
}
public static void main(String kork[])
{
new PayrollMDI();
}
}
class EmpEntry extends JFrame implements ActionListener
{
JLabel lblDash1,lblDash2;
JLabel lblSId,lblId,lblName,lblAddr,lblCon,lblEmail,lblJDt,lblSal,lblPost;
JTextField txtSId,txtId,txtName,txtAddr,txtCon,txtEmail,txtJDt,txtSal,txtPost;
JButton btnAddnew,btnSrch,btnEdit,btnDel,btnOk,btnCan,btnExit;
boolean f;
EmpEntry()
{
super("EPS - Employee Entry Form");
setLayout(null);
setBounds(350,200,500,420);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
setResizable(false);
add(lblDash1=new JLabel("-----------------------------------------------------------------------------------------------------------------------"));
add(lblDash2=new JLabel("-----------------------------------------------------------------------------------------------------------------------"));
lblDash1.setBounds(10,50,500,25);
lblDash2.setBounds(10,300,500,25);
add(lblSId=new JLabel("Employee Id"));
lblSId.setBounds(10,30,100,25);
add(txtSId=new JTextField());
txtSId.setBounds(120,30,100,25);
txtSId.setEnabled(false);
add(lblId=new JLabel("Employee Id"));
lblId.setBounds(10,70,100,25);
add(txtId=new JTextField());
txtId.setBounds(120,70,100,25);

add(lblJDt=new JLabel("Joining Dt"));
lblJDt.setBounds(270,70,100,25);
add(txtJDt=new JTextField());
txtJDt.setBounds(340,70,150,25);

add(lblName=new JLabel("Full Name"));
lblName.setBounds(10,100,100,25);
add(txtName=new JTextField());
txtName.setBounds(120,100,370,25);
add(lblAddr=new JLabel("Address"));
lblAddr.setBounds(10,130,100,25);
add(txtAddr=new JTextField());
txtAddr.setBounds(120,130,370,25);
add(lblCon=new JLabel("Contact No"));
lblCon.setBounds(10,160,100,25);
add(txtCon=new JTextField());
txtCon.setBounds(120,160,130,25);
add(lblEmail=new JLabel("Email Id"));
lblEmail.setBounds(270,160,130,25);
add(txtEmail=new JTextField());
txtEmail.setBounds(340,160,150,25);
add(lblPost=new JLabel("Post Name"));
lblPost.setBounds(10,190,100,25);
add(txtPost=new JTextField());
txtPost.setBounds(120,190,130,25);
add(lblSal=new JLabel("Salary ( D )"));
lblSal.setBounds(270,190,100,25);
add(txtSal=new JTextField());
txtSal.setBounds(340,190,150,25);

add(btnAddnew=new JButton("Add New"));
btnAddnew.setBounds(10,320,100,25);
btnAddnew.addActionListener(this);
add(btnOk=new JButton("OK"));
btnOk.setBounds(110,320,100,25);
btnOk.addActionListener(this);
btnOk.setEnabled(false);
add(btnCan=new JButton("Cancel"));
btnCan.setBounds(210,320,100,25);
btnCan.addActionListener(this);
btnCan.setEnabled(false);
add(btnSrch=new JButton("Search"));
btnSrch.setBounds(10,350,100,25);
btnSrch.addActionListener(this);
add(btnEdit=new JButton("Edit"));
btnEdit.addActionListener(this);
btnEdit.setEnabled(false);
btnEdit.setBounds(110,350,100,25);
add(btnDel=new JButton("Delete"));
btnDel.setBounds(210,350,100,25);
btnDel.addActionListener(this);
btnDel.setEnabled(false);
add(btnExit=new JButton("Exit"));
btnExit.setBounds(355,320,130,55);
btnExit.addActionListener(this);

}
void refresh()
{
txtSId.setText("");
txtId.setText("");
txtJDt.setText("");
txtName.setText("");
txtAddr.setText("");
txtCon.setText("");
txtEmail.setText("");
txtPost.setText("");
txtSal.setText("");
}
public void actionPerformed(ActionEvent ae)
{
try{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection con = DriverManager.getConnection("jdbc:odbc:prs");
Statement stmt = con.createStatement();
ResultSet rs;
//String s1="",s2="",s3="",s4="",s5="",s6="",s7="",s8="";
PreparedStatement pmt=null;
if(ae.getSource()==btnAddnew)
{
btnOk.setEnabled(true);
btnCan.setEnabled(true);
btnAddnew.setEnabled(false);
btnSrch.setEnabled(false);
txtId.requestFocus();
f=false;
}
else if(ae.getSource()==btnOk)
{
if(f)
{
pmt = con.prepareStatement("select * from emp_info where emp_id=?");
pmt.setInt(1,Integer.parseInt(txtSId.getText()));
rs = pmt.executeQuery();
if(rs.next())
{
txtId.setText(new Integer(rs.getInt(1)).toString());
txtName.setText(rs.getString(2));
txtAddr.setText(rs.getString(3));
txtCon.setText(rs.getString(4));
txtEmail.setText(rs.getString(5));
txtPost.setText(rs.getString(6));
txtSal.setText(rs.getString(7));
txtJDt.setText(rs.getString(8));
}
else
{
JOptionPane.showMessageDialog(this, "Record not found."  ,"Error", JOptionPane.ERROR_MESSAGE); 
}
}
else
{

pmt = con.prepareStatement("select emp_id from emp_info where emp_id=?");
pmt.setInt(1,Integer.parseInt(txtId.getText()));
rs = pmt.executeQuery();
if(!rs.next())
{
pmt=con.prepareStatement("insert into emp_info (emp_id,emp_name,emp_address,emp_contact,emp_mail,emp_post,emp_sal,emp_jdt) values(?,?,?,?,?,?,?,?)");
pmt.setInt(1,new Integer(txtId.getText()));
pmt.setString(2,txtName.getText());
pmt.setString(3,txtAddr.getText());
pmt.setString(4,txtCon.getText());
pmt.setString(5,txtEmail.getText());
pmt.setString(6,txtPost.getText());
pmt.setInt(7,Integer.parseInt(txtSal.getText()));
pmt.setString(8,txtJDt.getText());
pmt.executeUpdate();
JOptionPane.showMessageDialog(this, "Record added successfully..."  ,"Message", JOptionPane.PLAIN_MESSAGE); 
refresh();
}
else
{
JOptionPane.showMessageDialog(this, "Id already exists."  ,"Error", JOptionPane.ERROR_MESSAGE); 
txtId.requestFocus();
}
}
}

else if(ae.getSource()==btnEdit)
{
int dialogButton = JOptionPane.YES_NO_OPTION;
JOptionPane.showConfirmDialog (null, "Sure to edit ?","Confirmation",dialogButton);
if(dialogButton==JOptionPane.YES_OPTION)
{
pmt=con.prepareStatement("UPDATE emp_info SET emp_name=?,emp_address=?,emp_contact=?,emp_mail=?,emp_post=?,emp_sal=?,emp_jdt=? WHERE emp_id=?");
pmt.setString(8,txtId.getText());
pmt.setString(1,txtName.getText());
pmt.setString(2,txtAddr.getText());
pmt.setString(3,txtCon.getText());
pmt.setString(4,txtEmail.getText());
pmt.setString(5,txtPost.getText());
pmt.setString(6,txtSal.getText());
pmt.setString(7,txtJDt.getText());
pmt.executeUpdate();
JOptionPane.showMessageDialog(this, "Record edited successfully..."  ,"Message", JOptionPane.PLAIN_MESSAGE); 
refresh();
}

}
else if(ae.getSource()==btnCan)
{
btnOk.setEnabled(false);
btnCan.setEnabled(false);
btnAddnew.setEnabled(true);
btnSrch.setEnabled(true);
btnEdit.setEnabled(false);
btnDel.setEnabled(false);
txtSId.setEnabled(false);
refresh();
}
else if(ae.getSource()==btnDel)
{
int dialogButton = JOptionPane.YES_NO_OPTION;
JOptionPane.showConfirmDialog (null, "Sure to edit ?","Confirmation",dialogButton);
if(dialogButton==JOptionPane.YES_OPTION)
{
pmt=con.prepareStatement("delete * from emp_info where emp_id = ?");
pmt.setString(1,txtSId.getText());
pmt.executeUpdate();
JOptionPane.showMessageDialog(this, "Deleted successfully..."  ,"Message", JOptionPane.PLAIN_MESSAGE); 
refresh();
}
}
else if(ae.getSource()==btnSrch)
{
btnOk.setEnabled(true);
btnCan.setEnabled(true);
btnAddnew.setEnabled(false);
btnSrch.setEnabled(false);
btnEdit.setEnabled(true);
btnDel.setEnabled(true);
txtSId.setEnabled(true);
txtSId.requestFocus();
f=true;
}
else if(ae.getSource()==btnExit)
{
this.dispose();
}
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
/*public static void main(String kork[])
{
new EmpEntry();
}*/
}


class EmpRep extends JFrame implements ActionListener
{
JLabel lblDash1;
JLabel lblSId,lblName,lblAddr,lblCon,lblEmail,lblJDt,lblSal,lblPost;
JTextField txtSId;
JButton btnPrint,btnExit;
JTable tab; 
DefaultTableModel model;
JScrollPane sp;
boolean f;
EmpRep()
{
super("EPS - Employee Report");
setLayout(null);
setBounds(350,200,650,500);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setVisible(true);
setResizable(false);
add(lblDash1=new JLabel("PAYMENT DETAILS    -------------------------------------------------------------------------------------------------------------------------------"));
lblDash1.setBounds(10,80,650,25);
add(lblSId=new JLabel("Employee Id"));
lblSId.setBounds(10,30,100,25);
add(txtSId=new JTextField());
txtSId.setBounds(120,30,100,25);

add(lblJDt=new JLabel("JOINING DT : "));
lblJDt.setBounds(320,280,200,25);
add(lblName=new JLabel("FULL NAME : "));
lblName.setBounds(320,100,200,25);
add(lblAddr=new JLabel("ADDRESS   : "));
lblAddr.setBounds(320,130,200,25);
add(lblCon=new JLabel("CONTACT NO : "));
lblCon.setBounds(320,160,200,25);
add(lblEmail=new JLabel("EMAIL ID : "));
lblEmail.setBounds(320,190,200,25);
add(lblPost=new JLabel("POST NAME : "));
lblPost.setBounds(320,220,200,25);
add(lblSal=new JLabel("SALARY (D) : "));
lblSal.setBounds(320,250,200,25);

add(btnPrint=new JButton("Print..."));
btnPrint.setBounds(400,30,100,25);
add(btnExit=new JButton("Exit"));
btnExit.setBounds(520,30,100,25);
model =  new DefaultTableModel();
tab=new JTable(model);
model.addColumn("MONTH");
model.addColumn("DAYS PRESENT");
model.addColumn("TO BE PAYED");
model.addRow(new Object[] { new String("Onkar"), "Kulak" });

add((sp=new JScrollPane(tab)));
sp.setBounds(10,100,300,350);
tab.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
txtSId.addActionListener(this);
btnPrint.addActionListener(this);
btnExit.addActionListener(this);
}
public void actionPerformed(ActionEvent ae)
{
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection con = DriverManager.getConnection("jdbc:odbc:prs");
Statement stmt = con.createStatement();
ResultSet rs,rs2;
int mc1=0,mc2=0,mc3=0,mc4=0,mc5=0,mc6=0,mc7=0,mc8=0,mc9=0,mc10=0,mc11=0,mc12=0;
int rate=1;
String tt;
PreparedStatement pmt=null,pmt2=null;
if(ae.getSource()==btnPrint)
{
int dialogButton = JOptionPane.YES_NO_OPTION;
JOptionPane.showConfirmDialog (null, "Sure to print ?","Confirmation",dialogButton);
if(dialogButton==JOptionPane.YES_OPTION)
{
JOptionPane.showMessageDialog(this, "Printer not found."  ,"Error", JOptionPane.ERROR_MESSAGE); 
}
}
else if(ae.getSource()==btnExit)
{
this.dispose();
}
else if(ae.getSource()==txtSId)
{
pmt = con.prepareStatement("select * from emp_info where emp_id=?");
pmt.setInt(1,Integer.parseInt(txtSId.getText()));
rs = pmt.executeQuery();
if(rs.next())
{
lblName.setText("FULL NAME : "+rs.getString(2));
lblAddr.setText("ADDRESS : "+rs.getString(3));
lblCon.setText("CONTACT : "+rs.getString(4));
lblEmail.setText("EMAIL ID : "+rs.getString(5));
lblPost.setText("POST : "+rs.getString(6));
lblSal.setText("SALARY (D) : "+rs.getString(7));
lblJDt.setText("JOINING DT : "+rs.getString(8));
int rc=model.getRowCount()-1;
while(rc>=0)
model.removeRow(rc--);
pmt2 = con.prepareStatement("select emp_sal from emp_info where emp_id=?");
pmt2.setInt(1,Integer.parseInt(txtSId.getText()));
rs2 = pmt2.executeQuery();
if(rs2.next())
rate = rs2.getInt(1);
pmt2 = con.prepareStatement("select dt from attendance where emp_id=?");
pmt2.setInt(1,Integer.parseInt(txtSId.getText()));
rs2 = pmt2.executeQuery();
while(rs2.next())
{
tt=rs2.getString(1).substring(3,5);
if(tt.equals("01"))
{mc1++;}
else if(tt.equals("02"))
mc2++;
else if(tt.equals("03"))
mc3++;
else if(tt.equals("04"))
mc4++;
else if(tt.equals("05"))
mc5++;
else if(tt.equals("06"))
mc6++;
else if(tt.equals("07"))
mc7++;
else if(tt.equals("08"))
mc8++;
else if(tt.equals("09"))
mc9++;
else if(tt.equals("10"))
mc10++;
else if(tt.equals("11"))
mc11++;
else if(tt.equals("12"))
mc12++;
}
model.addRow(new Object[] { "January", mc1 , mc1*rate});
model.addRow(new Object[] { "February", mc2 , mc2*rate});
model.addRow(new Object[] { "March", mc3 , mc3*rate});
model.addRow(new Object[] { "April", mc4 , mc4*rate});
model.addRow(new Object[] { "May", mc5 , mc5*rate});
model.addRow(new Object[] { "June", mc6 , mc6*rate});
model.addRow(new Object[] { "July", mc7 , mc7*rate});
model.addRow(new Object[] { "August", mc8 , mc8*rate});
model.addRow(new Object[] { "September", mc9 , mc9*rate});
model.addRow(new Object[] { "October", mc10 , mc10*rate});
model.addRow(new Object[] { "November", mc11 , mc11*rate});
model.addRow(new Object[] { "December", mc12 , mc12*rate});
}
else
{
JOptionPane.showMessageDialog(this, "Record not found."  ,"Error", JOptionPane.ERROR_MESSAGE); 
}
}
}
catch(Exception e)
{
System.out.println(e.toString());
}
}
public static void main(String[] kspl)
{
new EmpRep();
}
}