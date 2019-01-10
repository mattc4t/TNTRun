/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package tntrun.selectionget;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WEIntegration {

	private WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");

	protected Location[] getLocations(Player player) {
		try {
			return getPlayerSelection(player);
		} catch (Exception e) {
		}
		return null;
	}

	private Location[] getPlayerSelection(Player player) throws CommandException {
		Location[] locs = new Location[2];
		com.sk89q.worldedit.entity.Player bukkitPlayer = we.wrapPlayer(player);
		World world = new BukkitWorld(player.getWorld());

		LocalSession session = we.getWorldEdit().getSessionManager().get(bukkitPlayer);
		RegionSelector selector = session.getRegionSelector(world);

		Region region;

		try {
			region = selector.getRegion().clone();
			BlockVector3 minimumPoint = region.getMinimumPoint();
			Location minLocation = new Location(player.getWorld(), minimumPoint.getX(), minimumPoint.getY(), minimumPoint.getZ() );
			BlockVector3 maximumPoint = region.getMaximumPoint();
			Location maxLocation = new Location(player.getWorld(), maximumPoint.getX(), maximumPoint.getY(), maximumPoint.getZ() );
			locs[0] = minLocation;
			locs[1] = maxLocation;
			return locs;

		}  catch (IncompleteRegionException e) {
			player.sendMessage("Incomplete region selection");
			return locs;
		}
	}
}
