// Import các thư viện cần thiết
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Lớp QuanLySinhVien chứa toàn bộ logic quản lý sinh viên
public class StudentManager {
    private String fileName; // Tên file để lưu trữ dữ liệu

    // Constructor khởi tạo đối tượng QuanLySinhVien
    public StudentManager(String fileName) {
        this.fileName = fileName; // Gán tên file
        // Không gọi loadFromFile() nữa, chỉ kiểm tra file tồn tại
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File " + fileName + " không tồn tại. Tạo file mới.");
            try {
                file.createNewFile();
            } catch (IOException ex) {
                System.out.println("Lỗi khi tạo file: " + ex.getMessage());
            }
        } else {
            System.out.println("File " + fileName + " đã tồn tại.");
        }
    }

    // Phương thức đọc một trang sinh viên trực tiếp từ file
    public List<Student> getStudents(int page) {
        List<Student> students = new ArrayList<>();
        int start = (page - 1) * 10; // Vị trí bắt đầu của trang
        int end = start + 10;         // Vị trí kết thúc của trang
        int currentIndex = -1;        // Đếm số sinh viên đã đọc

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Bỏ qua dòng trống
                String[] parts = line.split(",");
                if (parts.length != 6) continue; // Bỏ qua dòng không đủ 6 trường

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String masv = parts[1].trim();
                    String hoten = parts[2].trim();
                    int tuoi = Integer.parseInt(parts[3].trim());
                    String diachi = parts[4].trim();
                    double diem = Double.parseDouble(parts[5].trim());
                    currentIndex++; // Tăng chỉ số sinh viên

                    // Chỉ thêm sinh viên vào danh sách nếu nằm trong trang cần hiển thị
                    if (currentIndex >= start && currentIndex < end) {
                        students.add(new Student(id, masv, hoten, tuoi, diachi, diem));
                    }
                    if (currentIndex >= end) break; // Thoát vòng lặp nếu đã đủ sinh viên cho trang
                } catch (NumberFormatException e) {
                    // Bỏ qua dòng lỗi
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return students;
    }

    // Phương thức đếm tổng số sinh viên trực tiếp từ file
    public int getTotalStudents() {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (line.split(",").length == 6) total++;
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return total;
    }

    // Phương thức kiểm tra mã sinh viên có tồn tại không, đọc trực tiếp từ file
    public boolean isMasvExists(String masv) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String currentMasv = parts[1].trim();
                    if (currentMasv.equals(masv)) return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return false;
    }

    // Phương thức lấy ID tiếp theo, đọc trực tiếp từ file
    private int getNextId() {
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        if (id > maxId) maxId = id;
                    } catch (NumberFormatException e) {
                        // Bỏ qua dòng lỗi
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return maxId + 1;
    }
    // Phương thức thêm sinh viên trực tiếp vào file
    public void addStudent(String masv, String hoten, int tuoi, String diachi, double diem) {
        int id = getNextId(); // Lấy ID tiếp theo
        String studentData = id + "," + masv + "," + hoten + "," + tuoi + "," + diachi + "," + diem;
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(studentData);
            bw.newLine();
            System.out.println("Thêm sinh viên thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi thêm sinh viên: " + e.getMessage());
        }
    }



    // Phương thức tìm sinh viên theo mã sinh viên, đọc trực tiếp từ file
    public Student findStudent(String masv) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String currentMasv = parts[1].trim();
                    if (currentMasv.equals(masv)) {
                        int id = Integer.parseInt(parts[0].trim());
                        String hoten = parts[2].trim();
                        int tuoi = Integer.parseInt(parts[3].trim());
                        String diachi = parts[4].trim();
                        double diem = Double.parseDouble(parts[5].trim());
                        return new Student(id, currentMasv, hoten, tuoi, diachi, diem);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return null;
    }

    // Phương thức cập nhật thông tin sinh viên
    public void updateStudent(String masv, String newHoten, int newTuoi, String newDiachi, double newDiem) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Đọc toàn bộ file và cập nhật dòng cần thiết
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    lines.add(line);
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String currentMasv = parts[1].trim();
                    if (currentMasv.equals(masv)) {
                        found = true;
                        int id = Integer.parseInt(parts[0].trim());
                        line = id + "," + masv + "," + newHoten + "," + newTuoi + "," + newDiachi + "," + newDiem;
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return;
        }

        // Ghi lại toàn bộ file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            if (found) {
                System.out.println("Đã cập nhật sinh viên thành công.");
            } else {
                System.out.println("Không tìm thấy sinh viên.");
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // Phương thức xóa sinh viên
    public void deleteStudent(String masv, String confirmation) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        // Đọc toàn bộ file và loại bỏ dòng cần xóa
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    lines.add(line);
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String currentMasv = parts[1].trim();
                    if (currentMasv.equals(masv)) {
                        found = true;
                        continue; // Bỏ qua dòng này (tương đương với xóa)
                    }
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return;
        }

        // Ghi lại toàn bộ file
        if (found) {
            if (confirmation.equalsIgnoreCase("có")||confirmation.equalsIgnoreCase("co")) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                    for (String line : lines) {
                        bw.write(line);
                        bw.newLine();
                    }
                    System.out.println("Đã xóa sinh viên.");
                } catch (IOException e) {
                    System.out.println("Lỗi khi ghi file: " + e.getMessage());
                }
            } else {
                System.out.println("Hủy xóa sinh viên.");
            }
        } else {
            System.out.println("Không tìm thấy sinh viên.");
        }
    }
}