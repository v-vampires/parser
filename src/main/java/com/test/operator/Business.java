package com.test.operator;

import com.test.util.ExcelEntity;

public class Business implements ExcelEntity
{
    private String name;
    private String address;
    private String phone;
    private String url;
    private String source;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getSource()
    {
        return source;
    }
    
    public void setSource(String source)
    {
        this.source = source;
    }
    public Business() {
	}
    
    public Business(String name, String address, String phone, String url,
			String source) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.url = url;
		this.source = source;
	}

    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Business other = (Business) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "name:"+name+",address:"+address+",phone:"+phone+",source:"+source+",url:"+url;
    }
}
