package com.jl.hangye;

public class quanxianObj {
	public String user;
	public String userName;
	public String objectId;
	public String objectName;
	public String objectClass;
	public String group;
	public String dataId;
	public String dataName;
	public String isGranted;
	public String objectLabel;
	public String groupLabel;
	public String isExpired;
	
	
	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getObjectId() {
		return objectId;
	}


	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}


	public String getObjectName() {
		return objectName;
	}


	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}


	public String getObjectClass() {
		return objectClass;
	}


	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}


	public String getGroup() {
		return group;
	}


	public void setGroup(String group) {
		this.group = group;
	}


	public String getDataId() {
		return dataId;
	}


	public void setDataId(String dataId) {
		this.dataId = dataId;
	}


	public String getDataName() {
		return dataName;
	}


	public void setDataName(String dataName) {
		this.dataName = dataName;
	}


	public String getIsGranted() {
		return isGranted;
	}


	public void setIsGranted(String isGranted) {
		this.isGranted = isGranted;
	}


	public String getObjectLabel() {
		return objectLabel;
	}


	public void setObjectLabel(String objectLabel) {
		this.objectLabel = objectLabel;
	}


	public String getGroupLabel() {
		return groupLabel;
	}


	public void setGroupLabel(String groupLabel) {
		this.groupLabel = groupLabel;
	}


	public String getIsExpired() {
		return isExpired;
	}


	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public boolean equals(Object obj) {
        if (obj instanceof quanxianObj) {
        	quanxianObj Name = (quanxianObj) obj;
        	boolean p=(user.equals(Name.getUser())&&objectName.equals(Name.getObjectName())&&objectClass.equals(Name.getObjectClass())&&isGranted.equals(Name.getIsGranted())&&objectLabel.equals(Name.getObjectLabel())&&isExpired.endsWith(Name.getIsExpired()));
            return p;
        }
        return super.equals(obj);
    }
	  public int hashCode() {
		  	String str=user+objectName+objectClass+objectClass+isExpired;
	        return str.hashCode();
	            
	    }

}
