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
    private int storeId;


    public OwnerPage(int ownerId, Connection con) {
        this.ownerId = ownerId;
        this.con = con;

        try {
            while (true) {

                System.out.println("\n\n\n*** 가게 관리 *** ");

                // 어떤 작업을 수행할지 입력받음
                System.out.print("\n무엇을 하시겠습니까? \n\n1. 가게 관리 \n2. 가게 등록\n2. 로그아웃\n\n입력 : ");
                Scanner s = new Scanner(System.in);
                int work = s.nextInt();

                // 입력받은 작업들을 수행
                if (work == 1) {

                    // 쿼리문 작성 및 실행
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Store;");
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


                    System.out.print("\n\n무엇을 하시겠습니까? \n\n1. 날짜별 주문 목록 확인 \n2. 주문 완료되지 않은 주문 목록 확인 \n3. 메뉴 추가 \n4. 메뉴 삭제 \n5. 가게 삭제 \n6. 뒤로 가기\n\n입력 : ");
                    Scanner st2 = new Scanner(System.in);
                    int work2 = st2.nextInt();

                    if (work2 == 1) {
                        stmt = con.createStatement();
                        rs = stmt.executeQuery("SELECT * FROM Orders where store = " + storeId + ";");
                        // 성공시 검색 결과 출력, 실패시 오류 출력
                        System.out.printf("\n%-18s %-18s %-18s %-18s %-18s %-18s\n", "id", "배달/포장 여부", "주문 상태", "가게 요청 사항", "지불 방법", "가게 번호");
                        while (rs.next())
                            System.out.printf("%-16d %-16s %-16s %-16s %-16s %-16d\n\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(6), rs.getInt(7));


                    } else if (work2 == 2) {

                    } else if (work2 == 3) {

                    } else if (work2 == 4) {

                    } else if (work2 == 5) {

                    } else if (work2 == 6) {

                    }


                } // 데이터 삽입

                else if (work == 2) {
                    //가게 등록
                } // 데이터 삭제

                else if (work == 3) {
                    System.out.println("로그아웃 되었습니다.");
                    break;
                } // 데이터 삭제
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
