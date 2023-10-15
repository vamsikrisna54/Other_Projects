import javax.swing.*;

import java.util.*;
import java.io.*;
import java.awt.event.*;

class PersonInfo {
    String name;
    String email;
    String phoneNumber;

    PersonInfo(String n, String e, String p) {
        name = n;
        email = e;
        phoneNumber = p;
    }

    void display() {
        JOptionPane.showMessageDialog(null, "Name: " + name + "\n\nemail: " + email + "\n\nPhone no: " + phoneNumber);
    }
}

class AddressBook implements ActionListener {

    JFrame app;
    JButton jbadd, jbdel, jbsearch, jbshowall;
    String input, s;
    ArrayList persons;

    AddressBook() {
        createGUI();
        persons = new ArrayList();
        loadPersons();

    }

    public void createGUI() {
        JFrame app = new JFrame("Address Book");
        app.setVisible(true);
        app.setBounds(20, 20, 400, 300);
        app.setLayout(null);
        app.setResizable(false);

        jbadd = new JButton("Add");
        jbadd.setBounds(140, 40, 120, 50);
        app.add(jbadd);

        jbdel = new JButton("Delete");
        jbdel.setBounds(140, 100, 120, 50);
        app.add(jbdel);

        jbsearch = new JButton("Search");
        jbsearch.setBounds(140, 160, 120, 50);
        app.add(jbsearch);

        jbadd.addActionListener(this);
        jbdel.addActionListener(this);
        jbsearch.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == jbadd) {
            addPerson();
        } else if (e.getSource() == jbdel) {
            deletePerson();
        } else if (e.getSource() == jbsearch) {
            searchPerson();
        }
    }

    void addPerson() {
        String name = JOptionPane.showInputDialog("Enter name:");

        for (int i = 0; i < persons.size(); i++) {
            PersonInfo pr = (PersonInfo) persons.get(i);
            if (name.equals(pr.name)) {
                JOptionPane.showMessageDialog(null, "Name: " + name + " already exists please choose a new name");
                return;
            }
        }
        String email = JOptionPane.showInputDialog("Enter email:");
        if (email.length() != 0) {
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(null, "Email format error");
            }
        }
        String pNum = JOptionPane.showInputDialog("Enter phoneNo:");
        if (pNum.length() != 0) {
            if (pNum.length() != 10) {
                JOptionPane.showMessageDialog(null, "the given Phone No: " + pNum + " is not a valid phone number");
            }
        }
        PersonInfo p = new PersonInfo(name, email, pNum);
        persons.add(p);
        savePersons();
        JOptionPane.showMessageDialog(null, "the records of person by name:" + name + " saved to addressbook");

    }

    void searchPerson() {
        String n = JOptionPane.showInputDialog("Enter name:");

        for (int i = 0; i < persons.size(); i++) {
            PersonInfo p = (PersonInfo) persons.get(i);
            if (n.equals(p.name)) {
                p.display();
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "a person with the given name: " + n + " does not exist");

    }

    void deletePerson() {
        String n = JOptionPane.showInputDialog("Enter name:");

        for (int i = 0; i < persons.size(); i++) {
            PersonInfo p = (PersonInfo) persons.get(i);
            if (n.equals(p.name)) {

                persons.remove(i);
                JOptionPane.showMessageDialog(null, "deleted records of person by name: " + n + " from addressbook");
                return;
            }
        }
        JOptionPane.showMessageDialog(null,
                "did not find records of person by name: " + n + " to delete from addressbook");

    }

    void savePersons() {

        try {
            PersonInfo p;
            String line;
            FileWriter fw = new FileWriter("persons.txt");
            PrintWriter pw = new PrintWriter(fw);
            for (int i = 0; i < persons.size(); i++) {
                p = (PersonInfo) persons.get(i);
                line = p.name + "," + p.email + "," + p.phoneNumber;
                pw.println(line);
            }
            pw.flush();
            pw.close();
            fw.close();

        } catch (IOException ioEx) {
            System.out.println(ioEx);
        }

    }

    void loadPersons() {
        String tokens[] = null;
        String name, email, ph;

        try {
            FileReader fr = new FileReader("persons.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                tokens = line.split(",");
                name = tokens[0];
                email = tokens[1];
                ph = tokens[2];
                PersonInfo p = new PersonInfo(name, email, ph);
                persons.add(p);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException IoEx) {
            System.out.println(IoEx);
        }
    }

}

public class test {

    public static void main(String[] args) {
        new AddressBook();
    }
}