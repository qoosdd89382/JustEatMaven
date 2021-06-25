<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <!-- Custom fonts for this template-->
    <link href="<%=request.getContextPath()%>/vendors/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">
    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.css" rel="stylesheet">
</head>

<body class="bg-gradient-primary">

    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-xl-10 col-lg-12 col-md-9">

                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4">後臺登入</h1>
                                    </div>
                                    <form class="user" method="post" action="<%= request.getContextPath() %>/Dashboard/adminLoginHandler">
                                        <div class="form-group">
                                            <input type="email"
                                            	class='form-control form-control-user ${errorMsgs.get("loginAllowErr") == null ? "": "border-danger"}'
                                                id="exampleInputEmail" aria-describedby="emailHelp"
                                                placeholder='${errorMsgs.get("loginAllowErr") == null ? "請輸入e-mail": errorMsgs.get("loginAllowErr")}'
                                                name="adminMail" value="${adminMail}">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class='form-control form-control-user ${errorMsgs.get("loginAllowErr") == null ? "": "border-danger"}'
                                                id="exampleInputPassword"
                                                placeholder='${errorMsgs.get("loginAllowErr") == null ? "請輸入密碼": errorMsgs.get("loginAllowErr")}'
                                                name="adminPassword">
                                        </div>
<!--                                         <div class="form-group"> -->
<!--                                             <div class="custom-control custom-checkbox small"> -->
<!--                                                 <input type="checkbox" class="custom-control-input" id="customCheck"> -->
<!--                                                 <label class="custom-control-label" for="customCheck">Remember -->
<!--                                                     Me</label> -->
<!--                                             </div> -->
<!--                                         </div> -->
                                        <button type="submit" class="btn btn-primary btn-user btn-block">
                                            Login
                                        </button>
                                    </form>
                                    <hr>
                                    <div class="text-center">
                                        <a class="small" href="forgot-password.html">Forgot Password?</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>
    <!-- Bootstrap core JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/jquery/jquery-3.6.0.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="<%=request.getContextPath()%>/vendors/jquery-easing/jquery.easing.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/datatables/jquery.dataTables.min.js"></script>
    <script src="<%=request.getContextPath()%>/vendors/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.js"></script>
    <script src="js/datatables-demo.js"></script>
</body>
</html>