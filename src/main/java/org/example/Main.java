package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main
{



    public static void main(String args[])
    {
        Connection con=null;
        PreparedStatement prep_stmt=null;
        Statement stmt=null;

        try{
            // madang 데이터베이스와 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(
                    "jdbc:mysql://192.168.16.3:4567/delivery",
                    "jhkim","qaz123qaz");



            while(true) {



                // 어떤 작업을 수행할지 입력받음
                System.out.print("\n\n\n*** 배달의 종족 *** \n무엇을 하시겠습니까? \n\n1. 로그인 \n2. 회원 가입 \n3. 종료 \n\n입력 : ");
                Scanner s = new Scanner(System.in);
                int work = s.nextInt();


                // 입력받은 작업들을 수행
                if(work==1){
                    try{
                        // 유저, 사장님, 라이더 중 선택
                        System.out.print("\n\n\n사용자 유형을 선택하세요. \n\n1. 일반 유저 \n2. 사장님 \n3. 라이더 \n\n입력 : ");
                        Scanner t = new Scanner(System.in);
                        int type = t.nextInt();
                        // 삽입할 데이터 정보 입력받기
                        System.out.print("아이디를 입력하세요 : ");
                        Scanner sc1 = new Scanner(System.in);
                        Integer id = sc1.nextInt();
                        System.out.print("비밀번호를 입력하세요 : ");
                        Scanner sc2 = new Scanner(System.in);
                        String pwd = sc2.nextLine();

                        // 쿼리문 작성 및 실행
                        stmt=con.createStatement();
                        if(type==1) {
                            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE id = " + id + " AND pwd LIKE '" + pwd + "';");

                            if (rs.next() && !rs.next()) {
                                UserPage userPage = new UserPage(id, con);
                            }
                            else {
                                System.out.print("잘못된 입력입니다.");
                            }
                        }
                        else if(type==2) {
                            ResultSet rs = stmt.executeQuery("SELECT * FROM Owner WHERE id = " + id + " AND pwd LIKE '" + pwd + "';");

                            if (rs.next() && !rs.next()) {
                                OwnerPage ownerPage = new OwnerPage(id, con);
                            }
                            else {
                                System.out.print("잘못된 입력입니다.");
                            }
                        }
                        else if(type==3) {
                            ResultSet rs = stmt.executeQuery("SELECT * FROM Rider WHERE id = " + id + " AND pwd LIKE '" + pwd + "';");

                            if (rs.next() && !rs.next()) {
                                RiderPage riderPage = new RiderPage(id, con);
                            }
                            else {
                                System.out.print("잘못된 입력입니다.");
                            }
                        }




//                        // 성공시 검색 결과 출력, 실패시 오류 출력
//                        System.out.println("\n검색 결과 : ");
//                        while(rs.next())
//                            System.out.println(rs.getInt(1)+" "+rs.getString(2)+
//                                    " "+rs.getString(3));
//                        System.out.println();


//                        // 쿼리문 작성
//                        prep_stmt=con.prepareStatement(
//                                "INSERT INTO Book(bookname, publisher) VALUES ('"+userId+"', '"+userPwd+"');");
//
//                        // 쿼리문 실행
//                        prep_stmt.execute();
//
//                        // 성공시 결과 출력, 실패시 오류 출력
//                        System.out.println("입력되었습니다.\n");
                    }catch(Exception e){ System.out.println(e);}

                } // 데이터 삽입

                else if(work==2){

                    try{

                        // 삭제할 데이터 정보 입력받기
                        System.out.print("책 이름을 입력하세요 : ");
                        Scanner sc1 = new Scanner(System.in);
                        String bookname = sc1.nextLine();

                        // 쿼리문 작성
                        prep_stmt=con.prepareStatement("DELETE FROM Book WHERE bookname = '"+bookname+"';");

                        // 쿼리문 실행
                        prep_stmt.execute();

                        // 성공시 결과 출력, 실패시 오류 출력
                        System.out.println("삭제되었습니다.\n");
                    }catch(Exception e){ System.out.println(e);}
                } // 데이터 삭제

                else if(work==3){
                    try{

                        // 검색할 키워드 입력받기
                        System.out.print("검색어를 입력하세요 : ");
                        Scanner sc = new Scanner(System.in);
                        String keyword = sc.nextLine();

                        // 쿼리문 작성 및 실행
                        stmt=con.createStatement();
                        ResultSet rs=stmt.executeQuery("SELECT * FROM Book WHERE bookname LIKE '%" + keyword + "%';");

                        // 성공시 검색 결과 출력, 실패시 오류 출력
                        System.out.println("\n검색 결과 : ");
                        while(rs.next())
                            System.out.println(rs.getInt(1)+" "+rs.getString(2)+
                                    " "+rs.getString(3));
                        System.out.println();
                    }catch(Exception e){ System.out.println(e);}

                } // 데이터 검색

                else if(work==4){

                    try{

                        // 쿼리문 작성 및 실행
                        stmt=con.createStatement();
                        ResultSet rs=stmt.executeQuery("SELECT * FROM Menu");

                        // 성공시 결과 출력, 실패시 오류 출력
                        System.out.println();
                        while(rs.next())
                            System.out.println(rs.getInt(1)+" "+rs.getString(2)+
                                    " "+rs.getString(3));
                        System.out.println();
                    }catch(Exception e){ System.out.println(e);}

                } // 전체 데이터 출력

                else if(work==5) break; //종료

                else System.out.println("잘못된 입력입니다."); // 1~5 외 입력시 안내문구 출력
                //test
            }




            con.close();

            // 성공시 결과 출력, 실패시 오류 출력
            System.out.println("입력되었습니다.\n");
        }catch(Exception e){ System.out.println(e);}



    }
}

