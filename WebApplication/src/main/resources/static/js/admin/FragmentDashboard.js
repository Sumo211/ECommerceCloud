type = ['','info','success','warning','danger'];

demo = {
    initChartist: function(){
        $.ajax({
            url: "/admin/chartData",
            data: {
                type: 1
            },
            type: "GET",
            async: false,
            success: function (data) {
                new Chartist.Line('#income',
                {
                    labels: data.label,
                    series: [data.values]
                },
                {
                    fullWidth: true,
                    chartPadding: {
                        right: 40
                    },
                    lineSmooth: false,
                    axisY: {
                        offset: 80,
                        labelInterpolationFnc: function (value) {
                            return value + 'VND';
                        }
                    }
                });
            }
        });

        $.ajax({
            url: "/admin/chartData",
            data: {
                type: 2
            },
            type: "GET",
            async: false,
            success: function (data) {
                new Chartist.Line('#totalCloth', {
                    labels: data.label,
                    series: [data.values]
                }, {
                    fullWidth: true,
                    chartPadding: {
                        right: 40
                    }
                });
            }
        });
    }

};

