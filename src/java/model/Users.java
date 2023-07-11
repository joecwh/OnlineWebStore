/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static enumeration.AccountCode.ACCOUNT_PENDING;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.userid = :userid")
    , @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username")
    , @NamedQuery(name = "Users.findByFullname", query = "SELECT u FROM Users u WHERE u.fullname = :fullname")
    , @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email")
    , @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password")
    , @NamedQuery(name = "Users.findByContact", query = "SELECT u FROM Users u WHERE u.contact = :contact")
    , @NamedQuery(name = "Users.findByDob", query = "SELECT u FROM Users u WHERE u.dob = :dob")
    , @NamedQuery(name = "Users.findByAccountstatus", query = "SELECT u FROM Users u WHERE u.accountstatus = :accountstatus")
    , @NamedQuery(name = "Users.findByModifiedby", query = "SELECT u FROM Users u WHERE u.modifiedby = :modifiedby")
    , @NamedQuery(name = "Users.findByModifiedtime", query = "SELECT u FROM Users u WHERE u.modifiedtime = :modifiedtime")
    , @NamedQuery(name = "Users.findByCreatedat", query = "SELECT u FROM Users u WHERE u.createdat = :createdat")
    , @NamedQuery(name = "Users.findByLastlogin", query = "SELECT u FROM Users u WHERE u.lastlogin = :lastlogin")})
public class Users implements Serializable {

    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERID")
    private String userid;
    @Size(max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 255)
    @Column(name = "FULLNAME")
    private String fullname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "CONTACT")
    private String contact;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Size(max = 255)
    @Column(name = "ACCOUNTSTATUS")
    private String accountstatus;
    @Size(max = 255)
    @Column(name = "MODIFIEDBY")
    private String modifiedby;
    @Column(name = "MODIFIEDTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedtime;
    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;
    @Column(name = "LASTLOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogin;

    public Users() {
    }
    
    public Users(String userid, String username, String fullname, String email, String contact, Date dob, String accountstatus)
    {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        
        this.userid = userid;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.contact = contact;
        this.dob = dob;
        this.accountstatus = accountstatus;
        this.modifiedby = "";
        this.modifiedtime = currentTimestamp;
    }
    
    public Users(String username, String fullname, String email, String password, String contact, Date dob)
    {
        UUID uid = UUID.randomUUID();
        String uuid = uid.toString();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        
        this.userid = uuid;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.dob = dob;
        this.accountstatus = ACCOUNT_PENDING.name();
        this.createdat = currentTimestamp;
        this.lastlogin = currentTimestamp;
    }
    
    public Users(String username, String fullname, String email)
    {
        UUID uid = UUID.randomUUID();
        String uuid = uid.toString();
        
        String pid = UUID.randomUUID().toString();
        String password = pid.replaceAll("-", "").substring(0, 12);
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        
        this.userid = uuid;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.accountstatus = ACCOUNT_PENDING.name();
        this.createdat = currentTimestamp;
        this.lastlogin = currentTimestamp;
    }

    public Users(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Date getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(Date modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Users[ userid=" + userid + " ]";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
}
