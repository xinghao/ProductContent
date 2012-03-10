package com.airarena.products.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "providers" )
public class Provider extends BaseModel{
	
    private Long id;

    private String name;
    private String permalink;
//    private String source_object_id;
//    private Long provider_id;
//    private Long parent_id;
//    private int is_leaf;
    
    private Date created_at;
    private Date updated_at;

	public Provider() {
		// this form used by Hibernate
	}

	public Provider(String name) {
		// for application use, to create new events
		this.name = name;
		// [TODO] figure out how to generate permalink.
		this.permalink = name; 
		this.updated_at = this.created_at = new Date();
	}

	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
		return id;
    }

    private void setId(Long id) {
		this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	

}
