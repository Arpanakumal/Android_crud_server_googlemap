<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "android";

$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) die("Connection failed: " . $conn->connect_error);

$sql = "SELECT name, gender, email, rollno, address FROM students";
$result = $conn->query($sql);

$response = "";
if ($result->num_rows > 0) {
    while($row = $result->fetch_assoc()) {
        $response .= $row["name"] . "|" . $row["gender"] . "|" . $row["email"] . "|" . $row["rollno"] . "|" . $row["address"] . ";";
    }
}
echo $response;
$conn->close();
?>
