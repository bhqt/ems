### Tab
tabs组件，组件名：``l-tabs``

### 使用方式

在 ``template`` 中使用组件
#### 基本用法
```html
	<l-tabs :list="tabsList1"></l-tabs>
```
![基本用法.gif](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-2e4bdcac-2e0b-4664-a10c-7cab0b216e44/90e3d789-0be1-49a5-9533-01d0e3285e12.gif)

```html
	<!-- 传入对象  控制初始选中-->
	<l-tabs :list="tabsList2" keyName="name" v-model="active"></l-tabs>
```
![传入对象,控制初始选中.gif](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-2e4bdcac-2e0b-4664-a10c-7cab0b216e44/12234711-01c9-4009-8bbe-f9c30b91d875.gif)

```html
	<!--  带有下划线 -->
	<l-tabs :list="tabsList1" :lineShow="true" lineColor="#e9392f" :lineScale="1.2"></l-tabs>
```
![带有下划线.gif](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-2e4bdcac-2e0b-4664-a10c-7cab0b216e44/03ba9c9d-4849-4383-a73b-36b87bd09c43.gif)
```html
	<!-- 靠左排列  自定义选中文字颜色及大小  自定义下划线样式-->
		<l-tabs
			:list="TabsList"
			activeTextColor="#333"
			layout="left"
			keyName="name"
			:lineShow="true"
			:lineCrude="6"
			:activeSize="16"
			linePlace="26px"
			lineColor="linear-gradient(to right, #e9392f 30%, #f2d6d6)"
		></l-tabs>
```
![靠左排列，样式自定义.gif](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-2e4bdcac-2e0b-4664-a10c-7cab0b216e44/2f789f3c-22e4-4656-ab90-fa66dfccc8f5.gif)
```html
		<!-- tab标签过多 可滚动排列 -->
		<l-tabs :list="TabsList1" layout="scroll" keyName="name"></l-tabs>
```
		
![可滚动排列.gif](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-2e4bdcac-2e0b-4664-a10c-7cab0b216e44/776105f8-d985-4d2a-bad0-1f43525e0b17.gif)
```html
		<!-- 分割线 -->
		<l-tabs :list="tabsList2" :splitLine="true"  keyName="name"></l-tabs>
```
![分割线.gif](https://vkceyugu.cdn.bspapp.com/VKCEYUGU-2e4bdcac-2e0b-4664-a10c-7cab0b216e44/0488c288-7d6b-46cc-8773-9c293c481b53.gif)
	
```js
	export default {
	    data() {
	        return {
						active:2,
						tabsList1:['产品', '前端开发', '美工', '后端开发'],
						tabsList2: [{ name: '产品' }, { name: '前端开发' }, { name: '美工' }, { name: '后端开发' }],
					}
						
			}
	}
```

### 属性说明

|属性名					|类型								|默认值		|说明																																		|
|---						|----								|---			|---																																		|
|list						|array							|[]				|tab数据列表（可传数组对象）																						|
|value					|number							|0				|（ v-model）选中的index 不传下标从0开始																|
|keyName				|strig							|''				|当list 传数组对象时，展示文字的键名																		|
|layout					|strig							|'equal'	|tab 排列方式 <br>left‘ &#124; 'equal' &#124; 'scroll' 靠左  均分  滚动|
|tabWidth				|number							|375			|tab 宽度撑满屏幕即为375 组件内部转为100vw															|
|size						|number							|14				|非选中字体大小																													|
|activeSize			|number							|14				|选中状态字体大小																												|
|textColor			|string							|'#333'		|非选中字体颜色																													|
|activeTextColor|string							|'#E9392F'|选中状态字体颜色																												|
|bold						|number&#124;string	|400			|选中状态字体粗细(400&#124;500 &#124;600&#124;bold)											|
|labelPadding		|number							|5				|tabItem的左右padding																										|
|bk							|string							|'#FFF'		|tab 整体 的颜色																												|
|fixed					|boolean						|true			|tab 是否固定 （吸顶常用）																							|

### 下划线
|属性名		|类型		|默认值	|说明																			|
|---			|----		|---		|---																			|
|lineShow	|boolean|true		|下划线是否显示														|
|lineScale|number	|1			|下划线长度（伸缩比例），默认等于字体长度	|
|lineColor|string	|'#333'	|下划线颜色																|
|linePlace|string	|'35px'	|下划线位置																|
|lineCrude|string	|2			|下划线粗细																|


### 分割线
|属性名			|类型		|默认值	|说明														|
|---				|----		|---		|---														|
|splitLine	|boolean|true		|分割线是否显示（大于两个生效）	|
|slineColor	|string	|'#eee'	|下划线颜色											|