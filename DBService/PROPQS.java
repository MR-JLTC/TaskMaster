package taskmaster.DBService;

/**
 *
 * @author MR_JLTC
 */
public enum PROPQS{
    URL("jdbc:sqlite:resources/TaskBucket.db"),
    CREATE_TABLE_TASKINFO("""
                          CREATE TABLE IF NOT EXISTS TASKINFO(
                            TID integer unique primary key autoincrement,
                            BID integer,
                            task varchar(300),
                            foreign key(BID) references BUCKETLIST_INFO(BID)
                          );
                          """),
    
    CREATE_TABLE_BUCKETLIST_INFO("""
                                  CREATE TABLE IF NOT EXISTS BUCKETLIST_INFO(
                                    BID integer unique primary key autoincrement,
                                    Name varchar(100)
                                  );
                                 """),
    
    CREATE_VIEW_TASKINFO("""
                         CREATE VIEW IF NOT EXISTS TASK_INFO_VIEWER AS
                         SELECT TID, BUCKETLIST_INFO.NAME as BUCKETLIST, TASK from TASKINFO
                         INNER JOIN BUCKETLIST_INFO ON TASKINFO.BID=BUCKETLIST_INFO.BID;
                         """);
    
    
    private final String query;
    PROPQS(String q){
        this.query = q;
    }
    
    public String getQuery(){
        return this.query;
    }
}
