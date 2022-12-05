package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderAndReview {

    private int userId;
    private int storeId;
    private int orderId;
    private Connection con;
    private PreparedStatement prep_stmt = null;
    private Statement stmt = null;
    private ArrayList<Integer> menus = new ArrayList<Integer>();
    private ArrayList<Integer> counts = new ArrayList<Integer>();

    public OrderAndReview(int userId, int storeId, Connection con) {
        this.userId = userId;
        this.storeId = storeId;
        this.con = con;

        String deliveryOrTakeout;
        String requestToOwner;
        String requestToRider;
        String paymentMethod;

        while (true) {

            // 어떤 작업을 수행할지 입력받음
            System.out.println("\n\n무엇을 하시겠습니까? \n\n1. 메뉴 담기 \n2. 주문하기 \n3. 리뷰 작성 \n4. 뒤로가기\n\n입력 : ");
            Scanner s = new Scanner(System.in);
            int work = s.nextInt();


            // 입력받은 작업들을 수행
            if (work == 1) {
                System.out.print("\n메뉴 아이디를 입력하세요. \n\n입력 : ");
                Scanner st = new Scanner(System.in);
                int menuId = st.nextInt();
                menus.add(menuId);
                System.out.print("\n수량을 입력하세요. \n\n입력 : ");
                Scanner st2 = new Scanner(System.in);
                int count = st2.nextInt();
                counts.add(count);

            } // 데이터 삽입

            else if (work == 2) {
                while (true) {
                    System.out.print("\n\n배달과 포장 중 선택하세요. \n\n1. 배달 \n2. 포장\n\n입력 : ");
                    Scanner st = new Scanner(System.in);
                    int dOrT = st.nextInt();
                    if (dOrT == 1) {
                        deliveryOrTakeout = "배달";

                        System.out.print("\n사장님께 드릴 요청사항을 작성하세요. : ");
                        Scanner sc = new Scanner(System.in);
                        requestToOwner = sc.nextLine();

                        System.out.print("\n배달기사님께 드릴 요청사항을 작성하세요. : ");
                        Scanner sc2 = new Scanner(System.in);
                        requestToRider = sc2.nextLine();

                        while (true) {
                            System.out.print("\n결제 방식을 선택하세요. \n\n1. 현금 \n2. 카드\n\n입력 : ");
                            Scanner sc3 = new Scanner(System.in);
                            int pay = sc3.nextInt();
                            if (pay == 1) {
                                paymentMethod = "현금";
                                break;
                            } else if (pay == 2) {
                                paymentMethod = "카드";
                                break;
                            } else System.out.println("잘못된 입력입니다.");
                        }


                        try {
                            // 쿼리문 작성
                            prep_stmt = con.prepareStatement(
                                    "INSERT INTO Orders(delivery_or_takeout, status, request_to_store, request_to_rider, payment_method, store, user, rider) VALUES ('" + deliveryOrTakeout + "', '주문 접수 대기', '" + requestToOwner + "', '" + requestToRider + "', '" + paymentMethod + "', " + storeId + ", " + userId + ", 1);");
                            // 쿼리문 실행
                            prep_stmt.execute();


                            stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
                            if (rs.next()) {
                                orderId = rs.getInt(1);
                            }

                            for (int i = 0; i < menus.size(); i++) {
                                prep_stmt = con.prepareStatement(
                                        "INSERT INTO Order_Menu(order_id, menu_id, count) VALUES (" + orderId + ", " + menus.get(i) + ", " + counts.get(i) + ");");
                                // 쿼리문 실행
                                prep_stmt.execute();
                            }

                            // 성공시 결과 출력, 실패시 오류 출력
                            System.out.println("주문 접수되었습니다.\n");

                            while (true) {
                                System.out.print("\n\n무엇을 하시겠습니까? \n\n1. 주문 정보 보기 \n2. 목록으로 돌아가기\n\n입력 : ");
                                Scanner sc4 = new Scanner(System.in);
                                int work2 = sc4.nextInt();

                                if (work2 == 1) {

                                    stmt = con.createStatement();
                                    rs = stmt.executeQuery("SELECT * FROM Orders where id = " + orderId + ";");
                                    // 성공시 검색 결과 출력, 실패시 오류 출력
                                    System.out.printf("\n%-18s %-18s %-18s %-18s %-18s %-18s %-18s %-18s\n", "id", "배달/포장 여부", "주문 상태", "가게 요청 사항", "배달 요청 사항", "지불 방법", "가게 번호", "기사 번호");
                                    while (rs.next())
                                        System.out.printf("%-16d %-16s %-16s %-16s %-16s %-16s %-16d %-16d\n\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(9));


                                    rs = stmt.executeQuery("SELECT * FROM Order_Menu where order_id = " + orderId + ";");
                                    // 성공시 검색 결과 출력, 실패시 오류 출력
                                    System.out.printf("\n%-18s %-18s %-18s\n", "주문 번호", "메뉴 번호", "수량");
                                    while (rs.next())
                                        System.out.printf("%-16d %-16d %-16d\n\n", rs.getInt(1), rs.getInt(2), rs.getInt(3));


                                } // 데이터 삽입

                                else if (work2 == 2) {
                                    break;
                                } else System.out.println("잘못된 입력입니다.");
                            }

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        break;

                    } else if (dOrT == 2) { //테스트 필요
                        deliveryOrTakeout = "포장";

                        System.out.print("\n사장님께 드릴 요청사항을 작성하세요. : ");
                        Scanner sc = new Scanner(System.in);
                        requestToOwner = sc.nextLine();

                        System.out.print("\n결제 방식을 선택하세요. \n\n1. 현금 \n2. 카드\n\n입력 : ");
                        Scanner sc3 = new Scanner(System.in);
                        paymentMethod = sc3.nextLine();

                        try {
                            // 쿼리문 작성
                            prep_stmt = con.prepareStatement(
                                    "INSERT INTO Orders(delivery_or_takeout, status, request_to_store, payment_method, store, user) VALUES ('" + deliveryOrTakeout + "', '주문 접수 대기', '" + requestToOwner + "', '" + paymentMethod + "', " + storeId + ", " + userId + ");");
                            // 쿼리문 실행
                            prep_stmt.execute();


                            stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID();");
                            if (rs.next()) {
                                orderId = rs.getInt(1);
                            }

                            for (int i = 0; i < menus.size(); i++) {
                                prep_stmt = con.prepareStatement(
                                        "INSERT INTO Order_Menu(order_id, menu_id, count) VALUES (" + orderId + ", " + menus.get(i) + ", " + counts.get(i) + ");");
                                // 쿼리문 실행
                                prep_stmt.execute();
                            }

                            // 성공시 결과 출력, 실패시 오류 출력
                            System.out.println("주문 접수되었습니다.\n");

                            while (true) {
                                System.out.print("\n\n무엇을 하시겠습니까? \n\n1. 주문 정보 보기 \n2. 목록으로 돌아가기\n\n입력 : ");
                                Scanner sc4 = new Scanner(System.in);
                                int work2 = sc4.nextInt();

                                if (work2 == 1) {

                                    stmt = con.createStatement();
                                    rs = stmt.executeQuery("SELECT * FROM Orders where id = " + orderId + ";");
                                    // 성공시 검색 결과 출력, 실패시 오류 출력
                                    System.out.printf("\n%-18s %-18s %-18s %-18s %-18s %-18s\n", "id", "배달/포장 여부", "주문 상태", "가게 요청 사항", "지불 방법", "가게 번호");
                                    while (rs.next())
                                        System.out.printf("%-16d %-16s %-16s %-16s %-16s %-16d\n\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(6), rs.getInt(7));


                                    rs = stmt.executeQuery("SELECT * FROM Order_Menu where order_id = " + orderId + ";");
                                    // 성공시 검색 결과 출력, 실패시 오류 출력
                                    System.out.printf("\n%-18s %-18s %-18s\n", "주문 번호", "메뉴 번호", "수량");
                                    while (rs.next())
                                        System.out.printf("%-16d %-16d %-16d\n\n", rs.getInt(1), rs.getInt(2), rs.getInt(3));


                                } // 데이터 삽입

                                else if (work2 == 2) {
                                    break;
                                } else System.out.println("잘못된 입력입니다.");
                            }


                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        break;
                    } else System.out.println("잘못된 입력입니다.");
                }
            } // 데이터 삭제
            else if (work == 3) {
                //리뷰 작성

                try {

                    // 쿼리문 작성 및 실행
                    stmt = con.createStatement();
                    System.out.println("쿼리 전");
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Orders where store = " + storeId + " AND user = " + userId + ";");
                    // 성공시 검색 결과 출력, 실패시 오류 출력
                    System.out.println("쿼리 후");


                    int recentlyOrderId = 0;
                    while (rs.next()) {
                        System.out.println("쿼리 는" + rs.getInt(8));
                        recentlyOrderId = rs.getInt(1);
                    }
                    if (recentlyOrderId == 0) {
                        System.out.println("이 가게에 주문한 이력이 없습니다.");
                    } else {

                        System.out.println("여기?");


                        System.out.println("여기여");
                        stmt = con.createStatement();
                        rs = stmt.executeQuery("SELECT * FROM Review where order_id = " + recentlyOrderId + ";");

                        System.out.println("여기여기");
                        if (rs.next()) {
                            System.out.println("가장 최근 주문에 이미 리뷰를 남겼습니다.");
                        } else {


                            int star;
                            while (true) {
                                System.out.print("\n별점을 입력하세요. (1~5) : ");
                                Scanner sc = new Scanner(System.in);
                                star = sc.nextInt();
                                if (star >= 1 && star <= 5) break;
                            }

                            System.out.print("\n리뷰를 작성하세요. : ");
                            Scanner sc = new Scanner(System.in);
                            String text = sc.nextLine();

                            try {


                                // 쿼리문 작성
                                prep_stmt = con.prepareStatement(
                                        "INSERT INTO Review(order_id, star_rating, text) VALUES (" + recentlyOrderId + ", " + star + ", '" + text + "');");

                                // 쿼리문 실행
                                prep_stmt.execute();

                                // 성공시 결과 출력, 실패시 오류 출력
                                System.out.println("리뷰가 작성되었습니다..\n");
                            } catch (Exception e) {
                                System.out.println(e);
                            }


                        }


                    }

                    while (rs.next())
                        System.out.printf("%-4d %-10s %-5s %-10d %-17d %-19s %-25s\n\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getString(8) + " " + rs.getString(9));

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

                    OrderAndReview orderAndReview = new OrderAndReview(userId, storeId, con);

                } catch (Exception e) {
                    System.out.println(e);
                }


            } else if (work == 4) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}
