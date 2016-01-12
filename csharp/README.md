Project Setup
=============
Install NuGet Packages:
-------------
Install-Package EntityFramework7.Npgsql -Pre

Install-Package EntityFramework7.Npgsql.Design -Pre

Install-Package EntityFramework.Commands –Pre

Migrate DB schema:
-------------
Scaffold-DbContext -provider EntityFramework7.Npgsql -connection "Server=;Database=;userid=;password="
