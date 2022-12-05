package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String args[]) {
        Connection con = null;
        PreparedStatement prep_stmt = null;
        Statement stmt = null;

        try {
            // madang 데이터베이스와 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.16.3:4567/delivery",
                    "jhkim", "qaz123qaz");


            while (true) {


                // 어떤 작업을 수행할지 입력받음
                System.out.print("\n\n\n*** 배달의 종족 *** \n무엇을 하시겠습니까? \n\n1. 로그인 \n2. 회원 가입 \n3. 종료 \n\n입력 : ");
                Scanner s = new Scanner(System.in);
                int work = s.nextInt();


                // 입력받은 작업들을 수행
                if (work == 1) {

                    Integer id=0;
                    String pwd="";
                    Integer type=0;

                    // 유저, 사장님 중 선택
                    while(true) {
                        System.out.print("\n\n\n사용자 유형을 선택하세요. \n\n1. 일반 유저 \n2. 사장님 \n\n입력 : ");
                        Scanner t = new Scanner(System.in);
                        type = t.nextInt();

                        if (type == 1 || type == 2) {
                            break;

                        } else {
                            System.out.print("잘못된 입력입니다.");
                        }
                    }
                        // 삽입할 데이터 정보 입력받기
                        System.out.print("아이디를 입력하세요 : ");
                        Scanner sc1 = new Scanner(System.in);
                        id = sc1.nextInt();
                        System.out.print("비밀번호를 입력하세요 : ");
                        Scanner sc2 = new Scanner(System.in);
                        pwd = sc2.nextLine();

                    try {
                        // 쿼리문 작성 및 실행
                        stmt = con.createStatement();
                        if (type == 1) {
                            ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE id = " + id + " AND pwd LIKE '" + pwd + "';");

                            if (rs.next() && !rs.next()) {
                                UserPage userPage = new UserPage(id, con);
                            } else {
                                System.out.print("잘못된 입력입니다.");
                            }
                        } else if (type == 2) {
                            ResultSet rs = stmt.executeQuery("SELECT * FROM Owner WHERE id = " + id + " AND pwd LIKE '" + pwd + "';");

                            if (rs.next() && !rs.next()) {
                                OwnerPage ownerPage = new OwnerPage(id, con);
                            } else {
                                System.out.print("잘못된 입력입니다.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } // 데이터 삽입

                else if (work == 2) { //수정 필요

                    Integer id=0;
                    String pwd="";
                    String name, nickname = "", cellphone, email, roadname, detail, zipcode;
                    Integer type=0;

                    // 유저, 사장님 중 선택
                    while(true) {
                        System.out.print("\n\n\n사용자 유형을 선택하세요. \n\n1. 일반 유저 \n2. 사장님 \n\n입력 : ");
                        Scanner t = new Scanner(System.in);
                        type = t.nextInt();

                        if (type == 1 || type == 2) {
                            break;

                        } else {
                            System.out.print("잘못된 입력입니다.");
                        }
                    }

                    try {


                        // 유저, 사장님 중 선택
                        System.out.print("\n\n\n사용자 유형을 선택하세요. \n\n1. 일반 유저 \n2. 사장님 \n\n입력 : ");
                        Scanner t = new Scanner(System.in);
                        type = t.nextInt();
                        // 삽입할 데이터 정보 입력받기

                        System.out.print("비밀번호를 입력하세요 : ");
                        Scanner sc1 = new Scanner(System.in);
                        pwd = sc1.nextLine();

                        System.out.print("이름을 입력하세요 : ");
                        Scanner sc2 = new Scanner(System.in);
                        name = sc2.nextLine();

                        if (type == 1) {
                            System.out.print("닉네임 입력하세요 : ");
                            Scanner sc3 = new Scanner(System.in);
                            nickname = sc3.nextLine();
                        }

                        System.out.print("전화번호를 입력하세요 : ");
                        Scanner sc4 = new Scanner(System.in);
                        cellphone = sc4.nextLine();

                        System.out.print("이메일을 입력하세요 : ");
                        Scanner sc5 = new Scanner(System.in);
                        email = sc5.nextLine();

                        System.out.print("도로명을 입력하세요 : ");
                        Scanner sc6 = new Scanner(System.in);
                        roadname = sc6.nextLine();

                        System.out.print("상세 주소를 입력하세요 : ");
                        Scanner sc7 = new Scanner(System.in);
                        detail = sc7.nextLine();

                        System.out.print("우편번호를 입력하세요 : ");
                        Scanner sc8 = new Scanner(System.in);
                        zipcode = sc8.nextLine();

                        // 쿼리문 작성 및 실행
                        stmt = con.createStatement();
                        if (type == 1) {
                            try {


                                // 쿼리문 작성
                                prep_stmt = con.prepareStatement(
                                        "INSERT INTO User(pwd, name, nickname, cellphone, email, roadname, detail, zipcode) VALUES ('" + pwd + "', '" + name + "', '" + nickname + "', '" + cellphone + "', '" + email + "', '" + roadname + "', '" + detail + "', '" + zipcode + "');");

                                // 쿼리문 실행
                                prep_stmt.execute();

                                // 성공시 결과 출력, 실패시 오류 출력
                                System.out.println("회원 가입되었습니다.\n");

                                stmt = con.createStatement();
                                ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
                                if (rs.next()) {
                                    id = rs.getInt(1);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else if (type == 2) {
                            try {


                                // 쿼리문 작성
                                prep_stmt = con.prepareStatement(
                                        "INSERT INTO Owner(pwd, name, cellphone, email, roadname, detail, zipcode) VALUES ('" + pwd + "', '" + name + "', '" + cellphone + "', '" + email + "', '" + roadname + "', '" + detail + "', '" + zipcode + "');");

                                // 쿼리문 실행
                                prep_stmt.execute();

                                // 성공시 결과 출력, 실패시 오류 출력
                                System.out.println("\n회원 가입되었습니다.\n");

                                stmt = con.createStatement();
                                ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
                                if (rs.next()) {
                                    id = rs.getInt(1);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }

                        System.out.println("당신의 id는 " + id + ", 비밀번호는 " + pwd + "입니다.\n\n");

                    } catch (Exception e) {
                        System.out.println(e);
                    }


                } // 데이터 삭제

                else if (work == 3) break; //종료

                else System.out.println("잘못된 입력입니다."); // 1~5 외 입력시 안내문구 출력
                //test
            }


            con.close();

            // 성공시 결과 출력, 실패시 오류 출력
            System.out.println("입력되었습니다.\n");
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}

