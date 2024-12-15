package project.library.model;

import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass // without this createdOn and updatedOn column will not be created in the child db tables that are extending this class.
public class TimeStamp
{
    protected Date createdOn;
    protected Date updatedOn;
}
