<%@page import="enumeration.UserCode"%>
<%@page import="dto.UserDto"%>
<%@page import="dto.AdminDashboardDto"%>
<script src="https://kit.fontawesome.com/9d1d9a82d2.js" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
    <div class="container-fluid bg-dark" style="height: 180px; z-index: 0; position: absolute;">
        <p class="text-white fs-5 fw-bold mt-4 text-center">Manager's &nbsp;Dashboard</p>
    </div>

    <jsp:include page="AdminInitialServlet"/>
    <%
        AdminDashboardDto dashboardData = null;
        if(session.getAttribute("dashboardData") != null) 
        {
            dashboardData = (AdminDashboardDto) session.getAttribute("dashboardData");
        } 
    %>
        
    <div class="container" style="margin-top: 100px;">
        <div class='row'>
            <div class='col-sm-3 mb-3'>
                <div class="card mx-auto" style="width: 18rem;">
                    <div class="bg-primary badge" style="font-size: 70px;"><i class='' style='font-size:30px'>RM</i><%=dashboardData.getTotalRevenue() %></div>
                    <div class="card-body">
                        <p class="text-center card-text h5">Total Revenue</p>
                    </div>
                </div>
            </div>

            <div class='col-sm-3 mb-3'>
                <div class="card mx-auto" style="width: 18rem;">
                    <div class="bg-primary badge" style="font-size: 70px;"><i class='fas fa-male' style='font-size:30px'></i> <%=dashboardData.getTotalUser()%></div>
                    <div class="card-body">
                        <p class="text-center card-text h5">Total Customer</p>
                    </div>
                </div>
            </div>

            <div class='col-sm-3 mb-3'>
                <div class="card mx-auto" style="width: 18rem;">
                    <div class="bg-primary badge" style="font-size: 70px;"><i class='fa fa-newspaper-o' style='font-size:30px'></i> <%=dashboardData.getTotalSubscriber()%></div>
                    <div class="card-body">
                        <p class="text-center card-text h5">Total Subscriber</p>
                    </div>
                </div>
            </div>

            <div class="col-sm-3 mb-3">
                <div class="card mx-auto" style="width: 18rem;">
                    <div class="bg-primary badge" style="font-size: 70px;"><i class='fas fa-crosshairs' style='font-size:30px'></i> <%=dashboardData.getTotalOrderCompleted()%></div>
                    <div class="card-body">
                        <p class="text-center card-text h5">Total Order Completed</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row my-5">
            <div class='col-sm-6'>
                <canvas id="myChart" style="width:100%;max-width:600px"></canvas>
                <script>
                    var currentDate = new Date();
                    var currentYear = currentDate.getFullYear();
                    var futureYear = currentYear + 5;

                    var xValues = [currentYear-4, currentYear-3, currentYear-2, currentYear-1, currentYear];
                    var yValues = [ <%=String.format("%.2f", dashboardData.getTotalAnnualTransaction().get(4)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalAnnualTransaction().get(3)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalAnnualTransaction().get(2)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalAnnualTransaction().get(1)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalAnnualTransaction().get(0)) %>];
                    var barColors = ["red", "green","blue","orange","brown"];

                    new Chart("myChart", {
                        type: "bar",
                        data: {
                            labels: xValues,
                            datasets: [{
                                backgroundColor: barColors,
                                data: yValues
                            }]
                        },
                        options: {
                            legend: { display: false },
                            title: {
                                display: true,
                                text: "Annual Profit for 5 years"
                            },
                            scales: {
                                xAxes: [{
                                    scaleLabel: {
                                        display: true,
                                        labelString: "Years"
                                    }
                                }],
                                yAxes: [{
                                    scaleLabel: {
                                        display: true,
                                        labelString: "Ringgit Malaysia (RM)"
                                    }
                                }]
                            }
                        }
                    });
                </script>
            </div>
            <div class="col-sm-6">
                <canvas id="myChart2" style="width:100%;max-width:600px"></canvas>
                <script>
                    var currentYear2 = new Date().getFullYear();
                    const xValues2 = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
                    const yValues2 = [  <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(0)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(1)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(2)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(3)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(4)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(5)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(6)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(7)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(8)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(9)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(10)) %>, 
                                        <%=String.format("%.2f", dashboardData.getTotalMonthlyTransaction().get(11)) %>];

                    new Chart("myChart2", {
                      type: "line",
                      data: {
                        labels: xValues2,
                        datasets: [{
                          fill: false,
                          lineTension: 0,
                          backgroundColor: "rgba(0,0,255,1.0)",
                          borderColor: "rgba(0,0,255,0.1)",
                          data: yValues2
                        }]
                      },
                    options: {
                        title: {
                            display: true,
                            text: "Monthly Sales in " + currentYear2
                        },
                        legend: { display: false },
                        scales: {
                            yAxes: [{
                                ticks: {
                                    min: 0,
                                    max: 100000,
                                    callback: function (value) {
                                        return value / 1000 + "k";
                                    }
                                },
                                scaleLabel: {
                                    display: true,
                                    labelString: "Ringgit Malaysia (RM)"
                                }
                            }]
                        }
                    }
                    });
                </script>
            </div>
        </div>

        <div class="row g-2 my-5">
            <div class='col'>
                <canvas id="myChart3" class="mx-auto" style="width:100%;max-width:350px"></canvas>
                <script>
                    var xValues3 = ["<%=dashboardData.getDayOfWeek().get(0) %>",
                                    "<%=dashboardData.getDayOfWeek().get(1) %>",
                                    "<%=dashboardData.getDayOfWeek().get(2) %>",
                                    "<%=dashboardData.getDayOfWeek().get(3) %>",
                                    "<%=dashboardData.getDayOfWeek().get(4) %>",
                                    "<%=dashboardData.getDayOfWeek().get(5) %>",
                                    "<%=dashboardData.getDayOfWeek().get(6) %>"];
                                        
                    var yValues3 = [<%=String.format("%.2f", dashboardData.getTotalDailyTransaction().get(0)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalDailyTransaction().get(1)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalDailyTransaction().get(2)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalDailyTransaction().get(3)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalDailyTransaction().get(4)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalDailyTransaction().get(5)) %>, 
                                    <%=String.format("%.2f", dashboardData.getTotalDailyTransaction().get(6)) %>];
                    var barColors3 = [
                        "#FF0000",
                        "#00FFEC",
                        "#0032FF",
                        "#FBFF00",
                        "#3EFF00",
                        "#000000",
                        "#FF8B00"
                    ];

                    new Chart("myChart3", {
                      type: "pie",
                      data: {
                        labels: xValues3,
                        datasets: [{
                          backgroundColor: barColors3,
                          data: yValues3
                        }]
                      },
                      options: {
                        title: {
                          display: true,
                          text: "Total Daily Sales past 7 days"
                        }
                      }
                    });
                </script>
            </div>
            <div class='col-sm-6 text-center'>
                <canvas id="myChart4" style="width:100%;max-width:600px"></canvas>
                <script>
                const xValues4 = [];
                const yValues4 = [];
                // Generate values for 24 hours
                <% int i = 0; %>
                <% for (int day = 0; day < 31; day++) { %>
                    xValues4.push(<%= day + 1 %>);
                    // Generate random count between 0 and 100
                    yValues4.push(<%= dashboardData.getVisitorCount().get(i) %>);
                    <% i++; %>
                <% } %>

                var xyValues4 = xValues4.map((x, index) => ({ x: x, y: yValues4[index] }));

                new Chart("myChart4", {
                  type: "scatter",
                  data: {
                    datasets: [{
                      pointRadius: 4,
                      pointBackgroundColor: "rgb(0,0,255)",
                      data: xyValues4
                    }]
                  },
                  options: {
                      title: {
                        display: true,
                        text: "Daily Visitor Count for the year " + new Date().getFullYear()
                      },
                    legend: { display: false },
                    scales: {
                      xAxes: [{
                        scaleLabel: {
                          display: true,
                          labelString: "Days for 12 months"
                        },
                        ticks: {
                          min: 1,
                          max: 31,
                          stepSize: 1
                        }
                      }],
                      yAxes: [{
                        scaleLabel: {
                          display: true,
                          labelString: "Counts"
                        },
                        ticks: {
                          min: 0,
                          max: 100,
                          stepSize: 10
                        }
                      }]
                    }
                  }
                });
                </script>
            </div>
        </div>
    </div>