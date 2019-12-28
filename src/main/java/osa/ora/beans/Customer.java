package osa.ora.beans;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Customer implements Serializable{
	
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String city;
	private String state;
	private String birthday;
        private Accounts[] customerAccounts;

	public Customer(){
		
	}
	
	public Customer(long id, String firstName, String lastName, String email,
			String city, String state, String birthday) {
		super();
		this.id =id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.city = city;
		this.state = state;
		this.birthday = birthday;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
        @Override
    public String toString() {
        return "Customer {" + "id=" + id + ", firstName=" + firstName + 
                ", lastName=" + lastName + ", email=" + email + 
                ", city=" + city + ", birthday=" + birthday + 
                '}';
    }

    /**
     * @return the customerAccounts
     */
    public Accounts[] getCustomerAccounts() {
        return customerAccounts;
    }

    /**
     * @param customerAccounts the customerAccounts to set
     */
    public void setCustomerAccounts(Accounts[] customerAccounts) {
        this.customerAccounts = customerAccounts;
    }

}
