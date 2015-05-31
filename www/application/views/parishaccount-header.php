<!DOCTYPE html>
<html lang="en" ng-app="gceApp">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Go Catholic Events</title>
<link href="<?php echo base_url();?>css/gce.css" rel="stylesheet" />
<link href="<?php echo base_url();?>css/bootstrap.min.css"
	rel="stylesheet" />
<script src="<?php echo base_url();?>js/jquery-1.11.0.min.js"></script>
<script src="<?php echo base_url();?>js/bootstrap.min.js"></script>
<script src="<?php echo base_url();?>js/angular-1.3.min.js"></script>
<script src="<?php echo base_url();?>js/gceApp.js"></script>
<script
	src="<?php echo base_url();?>js/controllers/parishAccountController.js"></script>
</head>
<body ng-controller="parishAccountController" ng-init="init('<?php echo $church->id?>')">
    <?php require_once("navbar.php")?>
	<div id="divContainer" class="container">