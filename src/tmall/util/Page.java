package tmall.util;


/**
 * Page这个类专门为分页提供必要信息
 * 属性：
 * int start; 开始位置
 * int count; 每页显示的数量
 * int total; 总共有多少条数据
 * String param; 参数(这个属性在后续有用到，但是分类的分页查询里并没有用到，请忽略)
 *
 * 方法：
 * getTotalPage 根据 每页显示的数量count以及总共有多少条数据total，计算出总共有多少页
 * getLast 计算出最后一页的数值是多少
 * isHasPreviouse 判断是否有前一页
 * isHasNext 判断是否有后一页
 */
public class Page {
    int start;
    int count;
    int total;
    String param;

    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    public boolean isHasPreviouse() {
        if (start == 0)
            return false;
        return true;
    }

    public boolean isHasNext() {
        if (start == getLast()) {
            return false;
        }
        return true;
    }

    public int getTotalPage() {
        int totalPage;
        if (0 == total % count)
            totalPage = total / count;  //可以被整除
        else
            totalPage = total / count + 1; //总数不能被5整除， 多加一页

        if (0 == totalPage)
            totalPage = 1; //最少一页

        return totalPage;
    }

    public int getLast() {
        int last;
        if (0 == total % count)
            last = total - count; //50 为 5的整数倍 最后一页初始项为45
        else
            last = total - total % count; //51 最后一页从50开始
        last = last < 0 ? 0 : last;
        return last;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
