/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportid", query = "SELECT r FROM Report r WHERE r.reportid = :reportid")
    , @NamedQuery(name = "Report.findByReportname", query = "SELECT r FROM Report r WHERE r.reportname = :reportname")
    , @NamedQuery(name = "Report.findByCreatedat", query = "SELECT r FROM Report r WHERE r.createdat = :createdat")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "REPORTID")
    private String reportid;
    @Size(max = 255)
    @Column(name = "REPORTNAME")
    private String reportname;
    @Lob
    @Column(name = "REPORTPDF")
    private Serializable reportpdf;
    @Column(name = "CREATEDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;

    public Report() {
    }
    
    public Report(String reportname, Serializable reportpdf) 
    {
        LocalDateTime currentDateTime = LocalDateTime.now();
        this.reportid = UUID.randomUUID().toString();
        this.reportname = reportname;
        this.reportpdf = reportpdf;
        this.createdat = Timestamp.valueOf(currentDateTime);
    }

    public Report(String reportid) {
        this.reportid = reportid;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

    public String getReportname() {
        return reportname;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public Serializable getReportpdf() {
        return reportpdf;
    }

    public void setReportpdf(Serializable reportpdf) {
        this.reportpdf = reportpdf;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportid != null ? reportid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportid == null && other.reportid != null) || (this.reportid != null && !this.reportid.equals(other.reportid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Report[ reportid=" + reportid + " ]";
    }
    
}
