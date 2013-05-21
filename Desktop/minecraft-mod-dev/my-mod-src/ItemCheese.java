import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemCheese extends ItemFood {

    public ItemCheese(int id, int healAmount, float saturation, boolean favMeat) {
        super(id, healAmount, saturation, favMeat);
        this.setCreativeTab(CreativeTabs.tabFood);
    }


    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("Pasta:Cheese");


    }
}


