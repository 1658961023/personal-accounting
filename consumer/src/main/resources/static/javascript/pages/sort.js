    var tag=1;
    function sortNumberAs(a, b)
    {
        return a - b
    }
    function sortNumberDesc(a, b)
    {
        return b-a
    }

    function sortStrAS(a,b) {
        return a.localeCompare(b);
    }
    
    function sortStrDesc(a,b) {
        return b.localeCompare(a);

    }
    function SortTable(obj){
        var td0s=document.getElementsByName("td0");
        var td1s=document.getElementsByName("td1");
        var td2s=document.getElementsByName("td2");
        var td3s=document.getElementsByName("td3");
        var td4s=document.getElementsByName("td4");
        var td5s=document.getElementsByName("td5");
        var tdArray0=[];
        var tdArray1=[];
        var tdArray2=[];
        var tdArray3=[];
        var tdArray4=[];
        var tdArray5=[];
        for(var i=0;i<td0s.length;i++){
            tdArray0.push(td0s[i].innerHTML);
        }
        for(var i=0;i<td1s.length;i++){
            tdArray1.push(td1s[i].innerHTML);
        }
        for(var i=0;i<td2s.length;i++){
            tdArray2.push(td2s[i].innerHTML);
        }
        for(var i=0;i<td3s.length;i++){
            tdArray3.push(td3s[i].innerHTML);
        }
        for(var i=0;i<td4s.length;i++){
            tdArray4.push(td4s[i].innerHTML);
        }
        for(var i=0;i<td5s.length;i++){
            tdArray5.push(td5s[i].innerHTML);
        }
        var tds=document.getElementsByName("td"+obj.id.substr(2,1));
        var columnArray=[];
        for(var i=0;i<tds.length;i++){
            columnArray.push(tds[i].innerHTML);
        }
        var orginArray=[];
        for(var i=0;i<columnArray.length;i++){
            orginArray.push(columnArray[i]);
        }
        if(obj.className==="strAs"){
            columnArray.sort(sortStrAS);               //排序后的新值
            obj.className="strDesc";
        }else if (obj.className==="strDesc") {
            columnArray.sort(sortStrDesc);               //排序后的新值
            obj.className="strAs";
        }else if (obj.className === "numAs") {
            columnArray.sort(sortNumberAs);
            obj.className="numDesc"
        }else {
            columnArray.sort(sortNumberDesc);
            obj.className="numAs"
        }


        for(var i=0;i<columnArray.length;i++){
            for(var j=0;j<orginArray.length;j++){
                if(orginArray[j]===columnArray[i]){
                    document.getElementsByName("td0")[i].innerHTML=tdArray0[j];
                    document.getElementsByName("td1")[i].innerHTML=tdArray1[j];
                    document.getElementsByName("td2")[i].innerHTML=tdArray2[j];
                    document.getElementsByName("td3")[i].innerHTML=tdArray3[j];
                    document.getElementsByName("td4")[i].innerHTML=tdArray4[j];
                    document.getElementsByName("td5")[i].innerHTML=tdArray5[j];
                    orginArray[j]=null;
                    break;
                }
            }
        }

        for(var k=0;k<columnArray.length;k++){
            if(td0s[k].innerHTML==='收入'){
                td0s[k].style.color='green';
                td1s[k].style.color='green';
                td2s[k].style.color='green';
                td3s[k].style.color='green';
                td4s[k].style.color='green';
                td5s[k].style.color='green';
            }else{
                td0s[k].style.color='red';
                td1s[k].style.color='red';
                td2s[k].style.color='red';
                td3s[k].style.color='red';
                td4s[k].style.color='red';
                td5s[k].style.color='red';
            }
        }
    }