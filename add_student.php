<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "your_db_name";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get POST data
$name = $_POST['name'];
$gender = $_POST['gender'];
$email = $_POST['email'];
$rollno = $_POST['rollno'];
$address = $_POST['address'];

// Prevent duplicates by rollno
$stmt = $conn->prepare("SELECT * FROM students WHERE rollno = ?");
$stmt->bind_param("s", $rollno);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows == 0) {
    $stmt = $conn->prepare("INSERT INTO students (name, gender, email, rollno, address) VALUES (?, ?, ?, ?, ?)");
    $stmt->bind_param("sssss", $name, $gender, $email, $rollno, $address);
    $stmt->execute();
    echo "Student added";
} else {
    echo "Student already exists";
}

$conn->close();
?>
