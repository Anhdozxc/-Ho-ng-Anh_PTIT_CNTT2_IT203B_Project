package presentation;

import model.*;
import service.*;
import util.InputUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EmployeeMenu {
    private User currentUser;
    private UserService userService;
    private RoomService roomService;
    private EquipmentService equipmentService;
    private ServiceService serviceService;
    private BookingService bookingService;
    private DateTimeFormatter dateFormatter;

    public EmployeeMenu(User user) {
        this.currentUser = user;
        this.userService = new UserService();
        this.roomService = new RoomService();
        this.equipmentService = new EquipmentService();
        this.serviceService = new ServiceService();
        this.bookingService = new BookingService();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("  MENU NHAN VIEN");
            System.out.println("========================================");
            System.out.println("Xin chao: " + currentUser.getFullname());

            System.out.println("1. Dat phong moi");
            System.out.println("2. Xem lich cua toi");
            System.out.println("3. Huy lich");
            System.out.println("4. Xem phong kha dung");
            System.out.println("5. Xem dich vu");
            System.out.println("6. Cap nhat ho so");
            System.out.println("7. Dang xuat");

            int choice = InputUtil.inputChoice("Nhap lua chon: ", 1, 7);

            switch (choice) {
                case 1:
                    createNewBooking();
                    break;
                case 2:
                    viewMyBookings();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    viewAvailableRooms();
                    break;
                case 5:
                    viewAvailableServices();
                    break;
                case 6:
                    showProfileUpdate();
                    break;
                case 7:
                    return;
            }
        }
    }

    private void createNewBooking() {
        System.out.println("\n===== DAT PHONG =====");

        List<Room> rooms = roomService.getAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("Khong co phong");
            return;
        }

        for (Room r : rooms) {
            System.out.println(r.getId() + " - " + r.getName());
        }

        int roomId = InputUtil.inputPositiveInt("Chon phong: ");

        LocalDateTime start = InputUtil.inputDateTime("Bat dau: ");
        LocalDateTime end = InputUtil.inputDateTime("Ket thuc: ");

        int count = InputUtil.inputPositiveInt("So nguoi: ");
        String note = InputUtil.inputNonEmptyString("Ghi chu: ");

        int bookingId = bookingService.createBooking(
                currentUser.getId(), roomId, start, end, count, note
        );

        if (bookingId <= 0) {
            System.out.println("That bai");
        } else {
            System.out.println("Thanh cong ID = " + bookingId);
        }
    }

    private void viewMyBookings() {
        List<Booking> list = bookingService.getBookingsByUserId(currentUser.getId());

        for (Booking b : list) {
            System.out.println(b.getId() + " - " + b.getStatus());
        }
    }

    private void cancelBooking() {
        int id = InputUtil.inputPositiveInt("ID can huy: ");

        boolean ok = bookingService.cancelBooking(id);
        System.out.println(ok ? "Thanh cong" : "That bai");
    }

    private void viewAvailableRooms() {
        List<Room> list = roomService.getAvailableRooms();

        for (Room r : list) {
            System.out.println(r.getId() + " - " + r.getName());
        }
    }

    private void viewAvailableServices() {
        List<Service> list = serviceService.getActiveServices();

        for (Service s : list) {
            System.out.println(s.getId() + " - " + s.getName());
        }
    }

    private void showProfileUpdate() {
        System.out.println("\n===== CAP NHAT =====");

        System.out.println("1. Sua thong tin");
        System.out.println("2. Doi mat khau");

        int choice = InputUtil.inputChoice("Chon: ", 1, 2);

        if (choice == 1) updateProfile();
        else changePassword();
    }

    private void updateProfile() {
        String name = InputUtil.inputNonEmptyString("Ten: ");
        String phone = InputUtil.inputNonEmptyString("Phone: ");
        String dep = InputUtil.inputNonEmptyString("Department: ");

        boolean ok = userService.updateProfile(currentUser.getId(), name, phone, dep);

        System.out.println(ok ? "Thanh cong" : "That bai");
    }

    private void changePassword() {
        String oldPass = InputUtil.inputNonEmptyString("Old: ");
        String newPass = InputUtil.inputNonEmptyString("New: ");

        boolean ok = userService.changePassword(currentUser.getId(), oldPass, newPass);

        System.out.println(ok ? "Thanh cong" : "That bai");
    }
}