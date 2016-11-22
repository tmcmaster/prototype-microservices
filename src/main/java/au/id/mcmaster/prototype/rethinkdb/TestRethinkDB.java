package au.id.mcmaster.prototype.rethinkdb;

import java.util.Map;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;

import au.id.mcmaster.scratch.common.CommonUtils;


public class TestRethinkDB
{
    public static final RethinkDB r = RethinkDB.r;
    
    public static void main(String[] args)
    {
        Connection conn = r.connection().port(32770).connect();
        //r.dbCreate("marvel").run(conn);
        conn.use("example_app");
        //r.tableCreate("heroes").run(conn);
        //r.table("heroes").insert(r.hashMap("name", "John")).run(conn);
        Cursor changeCursor = r.table("messages").run(conn);
        for (Object change : changeCursor) {
            System.out.println(change);
        }
        get(conn);
        //update(conn);
        conn.close();
    }
    
    private static void get(Connection conn)
    {
        Map<String, Object> objectMap = r.table("messages").get("b3aa79ec-7abe-47b1-8061-26c465d06c41").run(conn);
        System.out.println("========================================");
        System.out.println(objectMap);
        MapObject updateMap = CommonUtils.generateUpdateMap(objectMap,objectMap);
        System.out.println(updateMap);
        System.out.println("========================================");
        r.table("messages").get("b3aa79ec-7abe-47b1-8061-26c465d06c41")
            .update(updateMap).run(conn);
        Map<String, Object> newObjectMap = r.table("messages").get("b3aa79ec-7abe-47b1-8061-26c465d06c41").run(conn);
        System.out.println(newObjectMap);
        System.out.println("========================================");
    }
    
    private static void update(Connection conn)
    {
        r.table("messages").get("b3aa79ec-7abe-47b1-8061-26c465d06c41")
            .update(r.hashMap("stuff", r.hashMap("a","aaaaa"))).run(conn);
    }
    

    private static void list(Connection conn)
    {
        Cursor changeCursor = r.table("messages").run(conn);
        for (Object change : changeCursor) {
            System.out.println(change);
        }
    }
    
    private static void monitor(Connection conn)
    {
        Cursor changeCursor = r.table("heroes").changes().run(conn);
        for (Object change : changeCursor) {
            System.out.println(change);
        }
    }
}
