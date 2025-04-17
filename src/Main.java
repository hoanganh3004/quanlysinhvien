// Import các thư viện cần thiết
import java.util.List;
import java.util.Scanner;

// Lớp Main chứa giao diện người dùng và gọi các phương thức từ QuanLySinhVien
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Tạo đối tượng Scanner để nhập liệu
        StudentManager qlsv = new StudentManager("students.csv"); // Tạo đối tượng quản lý sinh viên với file students.txt

        // Vòng lặp chính hiển thị menu
        while (true) {
            // Hiển thị menu
            System.out.println("\n---Quản Lý Sinh Viên----");
            System.out.println("1. Hiển thị danh sách sinh viên");
            System.out.println("2. Thêm sinh viên");
            System.out.println("3. Tìm kiếm sinh viên");
            System.out.println("4. Cập nhật thông tin sinh viên");
            System.out.println("5. Xóa sinh viên");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng: ");
            String choiceStr = scanner.nextLine().trim(); // Nhập lựa chọn và cắt khoảng trắng

            try {
                int choice = Integer.parseInt(choiceStr); // Chuyển lựa chọn thành số
                switch (choice) {
                    case 1: // Hiển thị danh sách sinh viên
                        int page = 1; // Bắt đầu từ trang 1
                        while (true) {
                            // Lấy danh sách sinh viên cho trang hiện tại
                            List<Student> students = qlsv.getStudents(page);
                            if (students.isEmpty() && page == 1) { // Nếu không có sinh viên và ở trang 1
                                System.out.println("Không có sinh viên để hiển thị.");
                                break;
                            }
                            // Hiển thị từng sinh viên trong trang
                            for (Student sv : students) {
                                sv.display();
                                System.out.println("--------------------------------------------------------------------------------------------"); // Dòng phân cách
                            }
                            // Hiển thị các lựa chọn phân trang
                            System.out.println("1. Tiếp theo");
                            System.out.println("2. Trước đó");
                            System.out.println("3. Quay lại");
                            String subChoiceStr = scanner.nextLine().trim(); // Nhập lựa chọn phân trang
                            int subChoice = Integer.parseInt(subChoiceStr); // Chuyển thành số
                            if (subChoice == 1) { // Tiếp theo
                                if (page * 10 < qlsv.getTotalStudents()) {
                                    page++; // Tăng số trang nếu chưa phải trang cuối
                                } else {
                                    System.out.println("Đây đã là trang cuối."); // Thông báo nếu đã là trang cuối
                                }
                            } else if (subChoice == 2) { // Trước đó
                                if (page > 1) {
                                    page--; // Giảm số trang nếu không phải trang đầu
                                } else {
                                    System.out.println("Đây đã là trang đầu."); // Thông báo nếu đã là trang đầu
                                }
                            } else if (subChoice == 3) { // Quay lại
                                break; // Thoát vòng lặp để quay lại menu chính
                            } else {
                                System.out.println("Lựa chọn không hợp lệ."); // Thông báo nếu nhập sai
                            }
                        }
                        break;

                    case 2: // Thêm sinh viên
                        System.out.print("Nhập mã sinh viên: ");
                        String masv = scanner.nextLine().trim(); // Nhập và cắt khoảng trắng
                        if (qlsv.isMasvExists(masv)) { // Kiểm tra mã sinh viên đã tồn tại chưa
                            System.out.println("Mã sinh viên đã tồn tại.");
                        } else {
                            System.out.print("Nhập họ tên: ");
                            String hoten = scanner.nextLine().trim(); // Nhập họ tên
                            System.out.print("Nhập tuổi: ");
                            String tuoiStr = scanner.nextLine().trim(); // Nhập tuổi
                            System.out.print("Nhập địa chỉ: ");
                            String diachi = scanner.nextLine().trim(); // Nhập địa chỉ
                            System.out.print("Nhập điểm trung bình: ");
                            String diemStr = scanner.nextLine().trim().replace(",", "."); // Nhập điểm, thay ',' bằng '.'
                            try {
                                int tuoi = Integer.parseInt(tuoiStr); // Chuyển tuổi thành số
                                if (tuoi <= 0) { // Kiểm tra tuổi hợp lệ
                                    System.out.println("Tuổi phải lớn hơn 0.");
                                    break;
                                }
                                double diem = Double.parseDouble(diemStr); // Chuyển điểm thành số
                                if (diem < 0 || diem > 10) { // Kiểm tra điểm trong khoảng 0-10
                                    System.out.println("Điểm trung bình phải từ 0 đến 10.");
                                } else {
                                    qlsv.addStudent(masv, hoten, tuoi, diachi, diem); // Thêm sinh viên
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Tuổi hoặc điểm trung bình không hợp lệ."); // Thông báo lỗi
                            }
                        }
                        break;

                    case 3: // Tìm kiếm sinh viên
                        System.out.print("Nhập mã sinh viên cần tìm: ");
                        String searchMasv = scanner.nextLine().trim(); // Nhập mã sinh viên
                        Student foundSv = qlsv.findStudent(searchMasv); // Tìm sinh viên
                        if (foundSv != null) { // Nếu tìm thấy
                            foundSv.display(); // Hiển thị thông tin
                        } else {
                            System.out.println("Không tìm thấy sinh viên."); // Thông báo không tìm thấy
                        }
                        break;

                    case 4: // Cập nhật thông tin sinh viên
                        System.out.print("Nhập mã sinh viên cần cập nhật: ");
                        String updateMasv = scanner.nextLine().trim(); // Nhập mã sinh viên
                        Student svToUpdate = qlsv.findStudent(updateMasv); // Tìm sinh viên
                        if (svToUpdate != null) { // Nếu tìm thấy
                            System.out.print("Nhập họ tên mới: ");
                            String newHoten = scanner.nextLine().trim(); // Nhập họ tên mới
                            System.out.print("Nhập tuổi mới: ");
                            String newTuoiStr = scanner.nextLine().trim(); // Nhập tuổi mới
                            System.out.print("Nhập địa chỉ mới: ");
                            String newDiachi = scanner.nextLine().trim(); // Nhập địa chỉ mới
                            System.out.print("Nhập điểm trung bình mới: ");
                            String newDiemStr = scanner.nextLine().trim().replace(",", "."); // Nhập điểm mới
                            try {
                                int newTuoi = Integer.parseInt(newTuoiStr); // Chuyển tuổi thành số
                                if (newTuoi <= 0) { // Kiểm tra tuổi hợp lệ
                                    System.out.println("Tuổi phải lớn hơn 0.");
                                    break;
                                }
                                double newDiem = Double.parseDouble(newDiemStr); // Chuyển thành số
                                if (newDiem < 0 || newDiem > 10) { // Kiểm tra điểm
                                    System.out.println("Điểm trung bình phải từ 0 đến 10.");
                                } else {
                                    qlsv.updateStudent(updateMasv, newHoten, newTuoi, newDiachi, newDiem); // Cập nhật
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Tuổi hoặc điểm trung bình không hợp lệ."); // Thông báo lỗi
                            }
                        } else {
                            System.out.println("Không tìm thấy sinh viên."); // Thông báo không tìm thấy
                        }
                        break;

                    case 5: // Xóa sinh viên
                        System.out.print("Nhập mã sinh viên cần xóa: ");
                        String deleteMasv = scanner.nextLine().trim(); // Nhập mã sinh viên
                        Student svToDelete = qlsv.findStudent(deleteMasv); // Tìm sinh viên
                        if (svToDelete != null) { // Nếu tìm thấy
                            svToDelete.display(); // Hiển thị thông tin sinh viên
                            System.out.print("Bạn có chắc chắn muốn xóa? (có/không): ");
                            String confirm = scanner.nextLine().trim(); // Nhập xác nhận
                            qlsv.deleteStudent(deleteMasv, confirm); // Gọi phương thức xóa
                        } else {
                            System.out.println("Không tìm thấy sinh viên."); // Thông báo không tìm thấy
                        }
                        break;

                    case 6: // Thoát chương trình
                        System.out.println("Được về nhà rồi :')");
                        scanner.close(); // Đóng scanner
                        System.exit(0);  // Thoát chương trình
                        break;

                    default: // Lựa chọn không hợp lệ
                        System.out.println("Chức năng không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ."); // Xử lý lỗi khi nhập không phải số
            }
        }
    }
}