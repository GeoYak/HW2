package org.example;

import org.example.database.H2Database;
import org.example.database.PostgreDatabase;
import org.example.logic.Tree;
import org.example.logic.TreeBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner in = new Scanner(System.in);
        H2Database h2Database = new H2Database();
        PostgreDatabase postgreDatabase = new PostgreDatabase();
        TreeBuilder treeBuilder = new TreeBuilder();

        Connection conn = null;
        List<Tree> treesFromData = null;
        int totalLeaves = 0;

        System.out.println("\n¬ведите к какой базе вы хотите подключитьс€ h2/postgre.");
        String data = in.nextLine();
        switch (data) {
            case "h2" -> {
                conn = h2Database.getConnection();
                treesFromData = treeBuilder.buildTreesFromDB(conn, "TREES");
                totalLeaves = treeBuilder.getTotalLeaves(treesFromData);
            }
            case "postgre" -> {
                conn = postgreDatabase.getConnection();
                treesFromData = treeBuilder.buildTreesFromDB(conn, "TREES");
                totalLeaves = treeBuilder.getTotalLeaves(treesFromData);
            }
        }

        if (conn != null) {
            //System.out.println(totalLeaves);
            try {
                FileWriter writer = new FileWriter("C:\\Users\\Georgy\\IdeaProjects\\HW2\\src\\output.csv");
                writer.write(String.valueOf(totalLeaves));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (data.equals("h2")) {
                h2Database.closeConnection(conn);
            } else {
                postgreDatabase.closeConnection(conn);
            }
        }

    }
}




