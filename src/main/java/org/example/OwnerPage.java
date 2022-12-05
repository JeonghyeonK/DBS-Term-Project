package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class OwnerPage {

    private int ownerId;
    private Connection con;
    private PreparedStatement prep_stmt = null;
    private Statement stmt = null;
    private ResultSet rs;
    private int storeId;
    private int menuId;


    public OwnerPage(int ownerId, Connection con) {
        this.ownerId = ownerId;
        this.con = con;

        try {
            while (true) {

                System.out.println("\n\n\n*** 가게 관리 *** ");

                // 어떤 작업을 수행할지 입력받음
                System.out.print("\n무엇을 하시겠습니까? \n\n1. 가게 관리 \n2. 가게 등록\n3. 가게 삭제\n4. 로그아웃\n\n입력 : ");
                Scanner s = new Scanner(System.in);
                int work = s.nextInt();

                // 입력받은 작업들을 수행
                if (work == 1) {

                    // 쿼리문 작성 및 실행
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Store where owner = " + ownerId + ";");
                    // 성공시 검색 결과 출력, 실패시 오류 출력
                    System.out.printf("\n%-4s %-10s %-5s %-8s %-14s %-16s %-25s\n", "id", "상호", "분류", "배달팁", "최소 주문 금액", "전화번호", "주소");
                    while (rs.next())
                        System.out.printf("%-4d %-10s %-5s %-10d %-17d %-19s %-25s\n\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(8) + " " + rs.getString(9));


                    // 유저, 사장님, 라이더 중 선택
                    System.out.print("\n가게 아이디를 입력하세요. \n\n입력 : ");
                    Scanner st = new Scanner(System.in);
                    storeId = st.nextInt();


                    rs = stmt.executeQuery("SELECT * FROM Menu where store = " + storeId + ";");
                    System.out.println("메뉴 목록");
                    System.out.printf("%-4s %-10s %-5s %-8s %-14s\n", "id", "분류", "이름", "설명", "가격");
                    while (rs.next())
                        System.out.printf("%-4d %-10s %-5s %-10s %-17d\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));

                    rs = stmt.executeQuery("SELECT * FROM Review where order_id IN (SELECT id FROM Orders where store = " + storeId + ");");
                    System.out.println("\n리뷰 목록");
                    System.out.printf("%-4s %-30s\n", "별점", "리뷰");
                    while (rs.next())
                        System.out.printf("%-4d %-30s\n\n", rs.getInt(3), rs.getString(4));

                    while (true) {
                        System.out.print("\n\n무엇을 하시겠습니까? \n\n1. 전체 주문 목록 확인 \n2. 주문 완료되지 않은 주문 목록 확인 및 변경 \n3. 메뉴 추가 \n4. 메뉴 삭제 \n5. 뒤로 가기\n\n입력 : ");
                        Scanner st2 = new Scanner(System.in);
                        int work2 = st2.nextInt();

                        if (work2 == 1) {
                            stmt = con.createStatement();
                            rs = stmt.executeQuery("SELECT * FROM Orders where store = " + storeId + ";");
                            // 성공시 검색 결과 출력, 실패시 오류 출력
                            System.out.printf("\n%-18s %-18s %-18s %-18s %-18s %-18s\n", "id", "배달/포장 여부", "주문 상태", "가게 요청 사항", "지불 방법", "가게 번호");
                            while (rs.next())
                                System.out.printf("%-16d %-16s %-16s %-16s %-16s %-16d\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(6), rs.getInt(7));

                        } else if (work2 == 2) {
                            stmt = con.createStatement();
                            rs = stmt.executeQuery("SELECT * FROM Orders where store = " + storeId + " AND status != '배달 완료';");
                            // 성공시 검색 결과 출력, 실패시 오류 출력
                            System.out.printf("\n%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s\n", "id", "배달/포장 여부", "주문 상태", "가게 요청 사항", "배달 요청 사항", "지불 방법", "가게 번호", "기사 번호");
                            while (rs.next())
                                System.out.printf("%-16d %-16s %-16s %-16s %-16s %-16s %-16d %-16d\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(9));

                            while (true) {
                                System.out.print("\n\n무엇을 하시겠습니까? \n\n1. 주문 상태 변경 \n2. 돌아가기\n\n입력 : ");
                                Scanner st3 = new Scanner(System.in);
                                int work3 = st3.nextInt();

                                if (work3 == 1) {
                                    int orderId;
                                    String status = "";
                                    System.out.print("\n주문 아이디를 입력하세요. \n\n입력 : ");
                                    Scanner st4 = new Scanner(System.in);
                                    orderId = st4.nextInt();

                                    System.out.print("\n\n변경하려는 상태를 입력하세요 \n\n1. 주문 접수 대기 \n2. 배달 준비중 \n3. 배달중 \n4. 배달 완료\n\n입력 : ");
                                    Scanner st5 = new Scanner(System.in);
                                    int stat = st5.nextInt();


                                    if (stat == 1) status = "주문 접수 대기";
                                    else if (stat == 2) status = "배달 준비중";
                                    else if (stat == 3) status = "배달중";
                                    else if (stat == 4) status = "배달 완료";

                                    stmt = con.createStatement();
                                    stmt.executeUpdate("UPDATE Orders SET status = '" + status + "' where id = " + orderId + ";");

                                    System.out.println("\n주문 상태가 변경되었습니다. \n");

                                } else if (work3 == 2) {
                                    break;
                                } else {
                                    System.out.println("잘못된 입력입니다.");
                                }
                            }


                        } else if (work2 == 3) {


                        } else if (work2 == 4) {

                            rs = stmt.executeQuery("SELECT * FROM Menu where store = " + storeId + ";");
                            System.out.println("메뉴 목록");
                            System.out.printf("%-4s %-10s %-5s %-8s %-14s\n", "id", "분류", "이름", "설명", "가격");
                            while (rs.next())
                                System.out.printf("%-4d %-10s %-5s %-10s %-17d\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));

                            rs = stmt.executeQuery("SELECT * FROM Review where order_id IN (SELECT id FROM Orders where store = " + storeId + ");");

                            System.out.print("메뉴 id를 입력하세요 : ");
                            Scanner sc1 = new Scanner(System.in);
                            menuId = sc1.nextInt();

                            rs = stmt.executeQuery("DELETE FROM Menu where id = "+menuId+";");

                            System.out.println("\n메뉴가 삭제되었습니다.\n\n");

                        } else if (work2 == 5) {
                            break;
                        } else {
                            System.out.println("잘못된 입력입니다.");
                        }
                    }

                } // 데이터 삽입

                else if (work == 2) {
                    String name;
                    String category = "";
                    int delivery_tip;
                    int minimum_money;
                    String phone;
                    String roadname;
                    String detail;
                    String zipcode;


                    System.out.print("비밀번호를 입력하세요 : ");
                    Scanner sc1 = new Scanner(System.in);
                    name = sc1.nextLine();

                    // 유저, 사장님 중 선택
                    System.out.print("\n\n\n사용자 유형을 선택하세요. \n\n1. 한식 \n2. 중식 \n3. 일식 \n4. 양식 \n5. 치킨 \n6. 피자 \n7. 기타\n\n입력 : ");
                    Scanner t = new Scanner(System.in);
                    int cate = t.nextInt();
                    if (cate == 1) {
                        category = "한식";
                    } else if (cate == 2) {
                        category = "중식";
                    } else if (cate == 3) {
                        category = "일식";
                    } else if (cate == 4) {
                        category = "양식";
                    } else if (cate == 5) {
                        category = "치킨";
                    } else if (cate == 6) {
                        category = "피자";
                    } else if (cate == 7) {
                        category = "기타";
                    }

                    System.out.print("최소 주문 금액을 입력하세요 : ");
                    Scanner sc2 = new Scanner(System.in);
                    minimum_money = sc2.nextInt();

                    System.out.print("배달팁을 입력하세요 : ");
                    Scanner sc3 = new Scanner(System.in);
                    delivery_tip = sc3.nextInt();

                    System.out.print("전화번호를 입력하세요 : ");
                    Scanner sc4 = new Scanner(System.in);
                    phone = sc4.nextLine();

                    System.out.print("도로명을 입력하세요 : ");
                    Scanner sc6 = new Scanner(System.in);
                    roadname = sc6.nextLine();

                    System.out.print("상세 주소를 입력하세요 : ");
                    Scanner sc7 = new Scanner(System.in);
                    detail = sc7.nextLine();

                    System.out.print("우편번호를 입력하세요 : ");
                    Scanner sc8 = new Scanner(System.in);
                    zipcode = sc8.nextLine();

                    rs = stmt.executeQuery("INSERT INTO Store(name, category, delivery_tip, minimum_money, cellphone, roadname, detail, zipcode) VALUES ('" + name + "', '" + category + "', " + delivery_tip + ", " + minimum_money + ", '" + phone + "', '" + roadname + "', '" + detail + "', '" + zipcode + "');");
                    rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
                    if (rs.next()) {
                        storeId = rs.getInt(1);
                    }

                    System.out.println("\n가게가 등록되었습니다. \n 가게 id : "+storeId+"\n");


                }

                else if (work == 3) {
                    System.out.print("가게 id를 입력하세요 : ");
                    Scanner sc1 = new Scanner(System.in);
                    storeId = sc1.nextInt();

                    rs = stmt.executeQuery("DELETE FROM Store where id = "+storeId+";");

                    System.out.println("\n가게가 삭제되었습니다.\n\n");
                }

                else if (work == 4) {
                    System.out.println("로그아웃 되었습니다.");
                    break;
                } // 데이터 삭제
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}