export default {
	/**
	 * 上滑加载更多数据的通用方法
	 * @param {Function} queryFunction - 查询数据的函数
	 * @param {Object} queryParams - 查询参数对象，必须包含pageNum和pageSize
	 * @param {Object} options - 可选配置项
	 * @param {Boolean} options.isFirstLoad - 是否是第一次加载
	 * @param {Array} options.currentList - 当前已有的数据列表
	 * @returns {Promise<Object>} 返回包含新数据、页码和加载状态的对象
	 */	async loadMoreData(queryFunction, queryParams, options = {}) {
		const {
			isFirstLoad = false,
			currentList = []
		} = options;

		let pageNum = queryParams.pageNum || 1;
		let pageSize = queryParams.pageSize || 10;
		let rows = [];
		let loadMoreStatus = 'more';
		let mergedRows = currentList;

		try {
			const response = await queryFunction({
				...queryParams
			});

			rows = response.rows || [];

			// 根据是否为第一次加载决定是替换还是追加数据
			if (isFirstLoad || pageNum === 1) {
				mergedRows = rows;
			} else {
				mergedRows = [...currentList, ...rows];
			}

			// 判断是否还有更多数据
			if (rows.length < pageSize) {
				loadMoreStatus = 'noMore';
			} else {
				loadMoreStatus = 'more';
				pageNum += 1;
			}

			return {
				rows: mergedRows,
				pageNum,
				loadMoreStatus,
				hasMore: loadMoreStatus === 'more'
			};
		} catch (error) {
			console.error('加载更多数据失败:', error);
			// 出错时保持原有页码和加载状态
			return {
				rows: mergedRows,
				pageNum,
				loadMoreStatus: 'more',
				hasMore: true,
				error
			};
		}
	}

}
