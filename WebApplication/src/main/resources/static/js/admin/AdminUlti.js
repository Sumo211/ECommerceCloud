var AdminUlti = {

    changePage : function(url,replaceElementID){
        $.ajax({
            url : url,
            type : "GET",
            dataType : "html",
            async : false,
            success : function(data){
                $("#"+replaceElementID).empty();
                $("#"+replaceElementID).append(data);
            }
        });
    }

};