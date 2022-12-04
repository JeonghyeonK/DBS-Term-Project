package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main
{


    public static void main(String args[])
    {

        while(true) {

            // 어떤 작업을 수행할지 입력받음
            System.out.println("작업을 입력하세요. \n1. 데이터 삽입 \n2. 데이터 삭제 \n3. 데이터 검색 \n4. 전체 데이터 출력 \n5. 종료");
            Scanner s = new Scanner(System.in);
            int work = s.nextInt();

            // 입력받은 작업들을 수행
            if(work==1){

                try{
                    // madang 데이터베이스와 연결
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection(
                            "jdbc:mysql://192.168.16.3:4567/madang",
                            "jhkim","qaz123qaz");

                    // 삽입할 데이터 정보 입력받기
                    System.out.print("책 이름을 입력하세요 : ");
                    Scanner sc1 = new Scanner(System.in);
                    String bookname = sc1.nextLine();
                    System.out.print("출판사를 입력하세요 : ");
                    Scanner sc2 = new Scanner(System.in);
                    String publisher = sc2.nextLine();

                    // 쿼리문 작성
                    PreparedStatement stmt=con.prepareStatement(
                            "INSERT INTO Book(bookname, publisher) VALUES ('"+bookname+"', '"+publisher+"');");

                    // 쿼리문 실행
                    stmt.execute();
                    con.close();

                    // 성공시 결과 출력, 실패시 오류 출력
                    System.out.println("입력되었습니다.\n");
                }catch(Exception e){ System.out.println(e);}

            } // 데이터 삽입

            else if(work==2){

                try{
                    // madang 데이터베이스와 연결
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection(
                            "jdbc:mysql://192.168.16.3:4567/madang",
                            "jhkim","qaz123qaz");

                    // 삭제할 데이터 정보 입력받기
                    System.out.print("책 이름을 입력하세요 : ");
                    Scanner sc1 = new Scanner(System.in);
                    String bookname = sc1.nextLine();

                    // 쿼리문 작성
                    PreparedStatement stmt=con.prepareStatement("DELETE FROM Book WHERE bookname = '"+bookname+"';");

                    // 쿼리문 실행
                    stmt.execute();
                    con.close();

                    // 성공시 결과 출력, 실패시 오류 출력
                    System.out.println("삭제되었습니다.\n");
                }catch(Exception e){ System.out.println(e);}
            } // 데이터 삭제

            else if(work==3){
                try{
                    // madang 데이터베이스와 연결
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection(
                            "jdbc:mysql://192.168.16.3:4567/madang",
                            "jhkim","qaz123qaz");
                    Statement stmt=con.createStatement();

                    // 검색할 키워드 입력받기
                    System.out.print("검색어를 입력하세요 : ");
                    Scanner sc = new Scanner(System.in);
                    String keyword = sc.nextLine();

                    // 쿼리문 작성 및 실행
                    ResultSet rs=stmt.executeQuery("SELECT * FROM Book WHERE bookname LIKE '%" + keyword + "%';");

                    // 성공시 검색 결과 출력, 실패시 오류 출력
                    System.out.println("\n검색 결과 : ");
                    while(rs.next())
                        System.out.println(rs.getInt(1)+" "+rs.getString(2)+
                                " "+rs.getString(3));
                    System.out.println();
                    con.close();
                }catch(Exception e){ System.out.println(e);}

            } // 데이터 검색

            else if(work==4){

                try{
                    // madang 데이터베이스와 연결
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con=DriverManager.getConnection(
                            "jdbc:mysql://192.168.16.3:4567/madang",
                            "jhkim","qaz123qaz");

                    // 쿼리문 작성 및 실행
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery("SELECT * FROM Book");

                    // 성공시 결과 출력, 실패시 오류 출력
                    System.out.println();
                    while(rs.next())
                        System.out.println(rs.getInt(1)+" "+rs.getString(2)+
                                " "+rs.getString(3));
                    System.out.println();
                    con.close();
                }catch(Exception e){ System.out.println(e);}

            } // 전체 데이터 출력

            else if(work==5) break; //종료

            else System.out.println("잘못된 입력입니다."); // 1~5 외 입력시 안내문구 출력
            //test
        }

    }
}

