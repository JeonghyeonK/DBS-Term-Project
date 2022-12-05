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
                while(true) {
                    System.out.print("\n\n배달과 포장 중 선택하세요. \n\n1. 배달 \n2. 포장\n\n입력 : ");
                    Scanner st = new Scanner(System.in);
                    int i = st.nextInt();
                    if (i == 1) {
                        deliveryOrTakeout = "배달";

                        System.out.print("\n사장님께 드릴 요청사항을 작성하세요. : ");
                        Scanner sc = new Scanner(System.in);
                        requestToOwner = sc.nextLine();

                        System.out.print("\n배달기사님께 드릴 요청사항을 작성하세요. : ");
                        Scanner sc2 = new Scanner(System.in);
                        requestToRider = sc2.nextLine();


                        break;
                    } else if (i == 2) {
                        deliveryOrTakeout = "포장";
                        break;
                    } else System.out.println("잘못된 입력입니다.");
                }
            } // 데이터 삭제
        }
    }
}
