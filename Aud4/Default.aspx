<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <style type="text/css">
        .auto-style1 {
            height: 103px;
        }
    </style>
</head>
<body>
    <form id="form1" runat="server">
    <div class="auto-style1">
    
        <asp:TextBox ID="username" runat="server"></asp:TextBox>
        <asp:RegularExpressionValidator ID="RegularExpressionValidator1" runat="server" ControlToValidate="username" ErrorMessage="RegularExpressionValidator" ValidationExpression="\+389 ((\d \d{4} \d{3})|(\d{2} \d{3} \d{3}))"></asp:RegularExpressionValidator>
        <asp:Button ID="Button1" runat="server" Text="Button" />
    
    </div>
        S</form>
</body>
</html>
