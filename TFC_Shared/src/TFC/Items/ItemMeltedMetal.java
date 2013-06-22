package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Util.StringUtil;

public class ItemMeltedMetal extends ItemTerra
{
	public ItemMeltedMetal(int i) 
	{
		super(i);
		setMaxDamage(100);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setFolder("ingots/");
	}	

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
	}

	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}

	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		if(is.getItemDamage() != 0)
			arraylist.add(StringUtil.localize("gui.MeltedMetal.NotFull"));
		super.addInformation(is, player, arraylist, flag);
		//Right Click to pour into a tool mold
		if(TFC_ItemHeat.getIsLiquid(is))
		{
			arraylist.add(EnumChatFormatting.AQUA + StringUtil.localize("gui.MeltedMetal.Inst0") + " " + 
					EnumChatFormatting.WHITE + StringUtil.localize("gui.MeltedMetal.Inst1"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(TFC_ItemHeat.getIsLiquid(itemstack))
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
			pi.specialCraftingType = itemstack.copy();

			itemstack.stackSize--;
			entityplayer.openGui(TerraFirmaCraft.instance, 38, world, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		}
		return itemstack;
	}
}
