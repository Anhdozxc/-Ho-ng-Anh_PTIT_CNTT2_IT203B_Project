package presentation;

import model.*;
import service.*;
import util.InputUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminMenu {
    private User currentUser;
    private UserService userService;
    private RoomService roomService;
    private EquipmentService equipmentService;

    public AdminMenu(User user) {
        this.currentUser = user;
        this.userService = new UserService();
        this.roomService = new RoomService();
        this.equipmentService = new EquipmentService();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println(" MENU QUAN TRI VIEN");
            System.out.println("========================================");
            System.out.println("Xin chao: " + currentUser.getFullname());

            System.out.println("1. Quan ly phong");
            System.out.println("2. Quan ly thiet bi");
            System.out.println("3. Quan ly nguoi dung");
            System.out.println("4. Dang xuat");

            int choice = InputUtil.inputChoice("Nhap lua chon: ", 1, 4);

            switch (choice) {
                case 1:
                    roomMenu();
                    break;
                case 2:
                    equipmentMenu();
                    break;
                case 3:
                    userMenu();
                    break;
                case 4:
                    return;
            }
        }
    }

    //  ROOM
    private void roomMenu() {
        while (true) {
            System.out.println("\n===== QUAN LY PHONG =====");
            System.out.println("1. Xem danh sach");
            System.out.println("2. Them phong");
            System.out.println("3. Sua phong");
            System.out.println("4. Xoa phong");
            System.out.println("0. Quay lai");

            int choice = InputUtil.inputChoice("Chon: ", 0, 4);

            switch (choice) {
                case 1:
                    viewRooms();
                    break;
                case 2:
                    addRoom();
                    break;
                case 3:
                    updateRoom();
                    break;
                case 4:
                    deleteRoom();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void viewRooms() {
        List<Room> list = roomService.getAllRooms();

        for (Room r : list) {
            System.out.println(r.getId() + " - " + r.getName()
                    + " - " + r.getCapacity()
                    + " - " + r.getLocation());
        }
    }

    private void addRoom() {
        String name = InputUtil.inputNonEmptyString("Ten: ");
        int capacity = InputUtil.inputPositiveInt("Suc chua: ");
        String location = InputUtil.inputNonEmptyString("Vi tri: ");
        String equipment = InputUtil.inputNonEmptyString("Thiet bi: ");

        boolean ok = roomService.addRoom(name, capacity, location, equipment);

        System.out.println(ok ? "Thanh cong" : "That bai");
    }

    private void updateRoom() {
        int id = InputUtil.inputPositiveInt("ID: ");
        String name = InputUtil.inputNonEmptyString("Ten moi: ");
        int capacity = InputUtil.inputPositiveInt("Suc chua moi: ");
        String location = InputUtil.inputNonEmptyString("Vi tri moi: ");
        String equipment = InputUtil.inputNonEmptyString("Thiet bi moi: ");

        boolean ok = roomService.updateRoom(id, name, capacity, location, equipment, "AVAILABLE");

        System.out.println(ok ? "Thanh cong" : "That bai");
    }

    private void deleteRoom() {
        int id = InputUtil.inputPositiveInt("ID: ");
        boolean ok = roomService.deleteRoom(id);
        System.out.println(ok ? "Thanh cong" : "That bai");
    }

    //  EQUIPMENT
    private void equipmentMenu() {
        while (true) {
            System.out.println("\n===== QUAN LY THIET BI =====");
            System.out.println("1. Xem");
            System.out.println("2. Them");
            System.out.println("3. Cap nhat so luong");
            System.out.println("0. Quay lai");

            int choice = InputUtil.inputChoice("Chon: ", 0, 3);

            switch (choice) {
                case 1:
                    viewEquipment();
                    break;
                case 2:
                    addEquipment();
                    break;
                case 3:
                    updateQuantity();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void viewEquipment() {
        List<Equipment> list = equipmentService.getAllEquipment();

        for (Equipment e : list) {
            System.out.println(e.getId() + " - " + e.getName()
                    + " - " + e.getAvailableQuantity());
        }
    }

    private void addEquipment() {
        String name = InputUtil.inputNonEmptyString("Ten: ");
        int quantity = InputUtil.inputPositiveInt("So luong: ");

        boolean ok = equipmentService.addEquipment(name, quantity, "ACTIVE");
        System.out.println(ok ? "Thanh cong" : "That bai");
    }

    private void updateQuantity() {
        int id = InputUtil.inputPositiveInt("ID: ");
        int quantity = InputUtil.inputPositiveInt("So luong moi: ");

        boolean ok = equipmentService.updateAvailableQuantity(id, quantity);
        System.out.println(ok ? "Thanh cong" : "That bai");
    }

    //  USER
    private void userMenu() {
        while (true) {
            System.out.println("\n===== QUAN LY USER =====");
            System.out.println("1. Tao support");
            System.out.println("0. Quay lai");

            int choice = InputUtil.inputChoice("Chon: ", 0, 1);

            switch (choice) {
                case 1:
                    createSupport();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void createSupport() {
        String username = InputUtil.inputNonEmptyString("Username: ");
        String password = InputUtil.inputNonEmptyString("Password: ");
        String fullname = InputUtil.inputNonEmptyString("Fullname: ");
        String phone = InputUtil.inputNonEmptyString("Phone: ");
        String department = InputUtil.inputNonEmptyString("Department: ");

        boolean ok = userService.createSupportStaffAccount(
                username, password, fullname, phone, department
        );

        System.out.println(ok ? "Thanh cong" : "That bai");
    }
}